package mercury.inventoryms.application.internal.commandservices;

import mercury.inventoryms.interfaces.rest.transform.ProductModelAssembler;
import mercury.shareDomain.Order;
import mercury.shareDomain.events.Backorder;
import mercury.inventoryms.interfaces.rest.ProductController;
import mercury.inventoryms.interfaces.rest.transform.PartModelAssembler;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import mercury.inventoryms.domain.aggregate.Part;
import mercury.inventoryms.domain.aggregate.Product;
import mercury.inventoryms.domain.valueObject.Manufacturer;
import mercury.inventoryms.infrastructure.repository.PartRepository;
import mercury.inventoryms.infrastructure.repository.ProductRepository;
import mercury.inventoryms.application.internal.outboundservices.BackorderEventPublisherService;
import mercury.inventoryms.application.internal.outboundservices.SupplierLookupService;
import mercury.inventoryms.application.internal.queryservices.ProductNotFoundException;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

// **********************************************************************
// COMMAND SERVICE
// called from the root aggregate in Product controller
// **********************************************************************
@Service
public class ProductInventoryCommandService {
  private ProductRepository productRepository;
  private PartRepository partRepository;
  private ProductModelAssembler assembler;
  private PartModelAssembler partAssembler;
  private SupplierLookupService supplierLookup;
  private BackorderEventPublisherService backorderEventPublisherService;

  public ProductInventoryCommandService(ProductRepository productRepository, PartRepository partRepository,
      ProductModelAssembler assembler, PartModelAssembler partAssembler, SupplierLookupService supplierLookup,
      BackorderEventPublisherService backorderEventPublisherService) {
    this.productRepository = productRepository;
    this.partRepository = partRepository;
    this.assembler = assembler;
    this.partAssembler = partAssembler;
    this.supplierLookup = supplierLookup;
    this.backorderEventPublisherService = backorderEventPublisherService;
  }

  // **********************************************************************
  // ADD PRODUCT
  // **********************************************************************
  public ResponseEntity<?> addProduct(Product newProduct) {
    EntityModel<Product> entityModel = assembler.toModel(productRepository.save(newProduct));
    System.out.println("**** PRODUCT ADDED ****");

    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  // **********************************************************************
  // ADD PRODUCT'S PART
  // **********************************************************************
  public ResponseEntity<?> addProductPart(Long id, Part part) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException(id));

    part.setProduct(product); // find the product in order to add it to part, bidirectional

    Manufacturer manufacturer = new Manufacturer(part.getManufacturer(),
        supplierLookup.fetchSupplierURI(part.getManufacturer()).toString());

    part.setManufacturer(manufacturer);
    partRepository.save(part);
    product.addPart(part);
    productRepository.save(product);

    System.out.println("**** PRODUCT PART ADDED ****");

    EntityModel<Product> entityModel = assembler.toModel(product);

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
  }

  // **********************************************************************
  // UPDATE PRODUCT
  // **********************************************************************
  public ResponseEntity<?> updateProduct(Product newProduct, Long id) {
    Product updatedProduct = productRepository.findById(id) //
        .map(product -> {
          product.setName(newProduct.getName());
          product.setPrice(newProduct.getPrice());
          product.setDescription(newProduct.getDescription());
          product.setQuantity(newProduct.getQuantity());
          return productRepository.save(product);
        }) //
        .orElseGet(() -> {
          newProduct.setId(id);
          return productRepository.save(newProduct);
        });

    System.out.println("**** PRODUCT UPDATED ****");
    EntityModel<Product> entityModel = assembler.toModel(updatedProduct);

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
  }

  // **********************************************************************
  // UPDATE PART
  // **********************************************************************
  public ResponseEntity<?> updatePart(Long productId, Long partId, Part newPart) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new ProductNotFoundException(productId));

    // after product is found, update the part then add product to part.
    Part updatedPart = partRepository.findById(partId) //
        .map(part -> {
          part.updatePart(newPart);
          return partRepository.save(part);
        }) //
        .orElseGet(() -> {
          newPart.setId(partId);
          return partRepository.save(newPart);
        });


    Manufacturer manufacturer = new Manufacturer(updatedPart.getManufacturer(),
        supplierLookup.fetchSupplierURI(updatedPart.getManufacturer()).toString());

    updatedPart.setManufacturer(manufacturer);
    updatedPart.setProduct(product);
    product.addPart(updatedPart);
    System.out.println("**** PRODUCT PART UPDATED ****");

    EntityModel<Part> entityModel = partAssembler.toModel(partRepository.save(updatedPart));

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
  }

  // **********************************************************************
  // CHECK AVAILABILITY ON SALE ORDER
  // **********************************************************************
  public Order processOrder(Order order) {

    List<Product> products = productRepository.findAll();

    Boolean productNameCheck = false;
    Boolean productQuantityCheck = false;
    Boolean partQuantityCheck = true;
    Product updateProduct = new Product();
    for (Product p : products) {
      if (order.getProductName().equals(p.getName())) {
        productNameCheck = true;
        updateProduct = p;
        if (order.getQuantity() <= p.getQuantity()) {
          productQuantityCheck = true;
          for (Part part : updateProduct.getParts()) {
            if (order.getQuantity() > part.getQuantity()) {
              partQuantityCheck = false;
              break;
            }
          }
        }

      }
    }
    if (!productNameCheck) {
      return new Order(
          order.getSaleID(),
          "PRODUCT NOT FOUND",
          order.getProductName(),
          order.getQuantity());
    }
    else if (productNameCheck && productQuantityCheck && partQuantityCheck) {
      updateProduct.setQuantity(updateProduct.getQuantity() - order.getQuantity());
      for (Part updatePart : updateProduct.getParts()) {
        updatePart.setQuantity(updatePart.getQuantity() - order.getQuantity());
      }
      productRepository.save(updateProduct);

      return new Order(
          order.getSaleID(),
          "COMPLETE",
          order.getProductName(),
          linkTo(methodOn(ProductController.class).getProduct(updateProduct.getId())).toUri(),
          order.getQuantity(),updateProduct.getPrice() * order.getQuantity(), new Date());
    }
    else if (productNameCheck && (!productQuantityCheck || !partQuantityCheck)) {
      return new Order(
          order.getSaleID(),
          "UNAVAILABLE",
          order.getProductName(),
          linkTo(methodOn(ProductController.class).getProduct(updateProduct.getId())).toUri(),
          order.getQuantity(), updateProduct.getPrice() * order.getQuantity(), new Date());
    }

    return order;
  }

public Order backorder(Order order) {
  backorderEventPublisherService.handleSaleBackorderEvent(new Backorder(order.getSaleID(), order.getTotal()));
  return order;
}

}
