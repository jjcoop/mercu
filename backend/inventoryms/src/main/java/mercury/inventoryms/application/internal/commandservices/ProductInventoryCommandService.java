package mercury.inventoryms.application.internal.commandservices;

import mercury.inventoryms.interfaces.rest.transform.ProductModelAssembler;
import mercury.shareDomain.Order;
import mercury.inventoryms.interfaces.rest.transform.PartModelAssembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import mercury.inventoryms.domain.aggregate.Product;
import mercury.inventoryms.domain.entity.Part;
import mercury.inventoryms.domain.valueObject.Manufacturer;
import mercury.inventoryms.infrastructure.repository.PartRepository;
import mercury.inventoryms.infrastructure.repository.ProductRepository;
import mercury.inventoryms.application.internal.outboundservices.SupplierLookupService;
import mercury.inventoryms.application.internal.queryservices.ProductNotFoundException;

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

  public ProductInventoryCommandService(ProductRepository productRepository, PartRepository partRepository,
      ProductModelAssembler assembler, PartModelAssembler partAssembler, SupplierLookupService supplierLookup) {
    this.productRepository = productRepository;
    this.partRepository = partRepository;
    this.assembler = assembler;
    this.partAssembler = partAssembler;
    this.supplierLookup = supplierLookup;
  }

  // **********************************************************************
  // ADD PRODUCT
  // **********************************************************************
  public ResponseEntity<?> addProduct(Product newProduct) {
    EntityModel<Product> entityModel = assembler.toModel(productRepository.save(newProduct));

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
    updatedPart.setProduct(product);

    Manufacturer manufacturer = new Manufacturer(updatedPart.getManufacturer(),
        supplierLookup.fetchSupplierURI(updatedPart.getManufacturer()).toString());

    updatedPart.setManufacturer(manufacturer);

    EntityModel<Part> entityModel = partAssembler.toModel(partRepository.save(updatedPart));

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
  }

  public Order processOrder(Order order) {
    System.out.println(order.getSaleID());
    System.out.println(order.getStatusCode());
    System.out.println(order.getProductName());
    System.out.println(order.getQuantity());
    System.out.println(order.getDateTime());
    return order;
  }

}
