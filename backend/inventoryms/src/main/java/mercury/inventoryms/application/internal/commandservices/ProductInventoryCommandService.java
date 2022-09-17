package mercury.inventoryms.application.internal.commandservices;

import mercury.inventoryms.interfaces.rest.transform.ProductModelAssembler;
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
import mercury.inventoryms.application.internal.queryservices.PartNotFoundException;
import mercury.inventoryms.application.internal.queryservices.ProductNotFoundException;

@Service
public class ProductInventoryCommandService {
  private ProductRepository productRepository;
  private PartRepository partRepository;
  private ProductModelAssembler assembler;
  private PartModelAssembler partAssembler;
  SupplierLookupService supplierLookup;

  public ProductInventoryCommandService(ProductRepository productRepository, PartRepository partRepository,
      ProductModelAssembler assembler, PartModelAssembler partAssembler, SupplierLookupService supplierLookup) {
    this.productRepository = productRepository;
    this.partRepository = partRepository;
    this.assembler = assembler;
    this.partAssembler = partAssembler;
    this.supplierLookup = supplierLookup;
  }

  public ResponseEntity<?> addProduct(Product newProduct) {

    EntityModel<Product> entityModel = assembler.toModel(productRepository.save(newProduct));

    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  public ResponseEntity<?> addProductPart(Long id, Part part) {

    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException(id));

    part.setProduct(product);

    Manufacturer manufacturer = new Manufacturer(part.getManufacturer(), supplierLookup.fetchSupplierURI(part.getManufacturer()).toString());

    String check = manufacturer.getURI().toString().split(":")[0];
    System.out.println(check);

    part.setManufacturer(manufacturer);
    partRepository.save(part);
    product.addPart(part);
    productRepository.save(product); 



    EntityModel<Product> entityModel = assembler.toModel(product);

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
  }

  public ResponseEntity<?> updateProduct(Product newProduct, Long id) {

    Product updatedProduct = productRepository.findById(id) //
        .map(product -> {
          product.setName(newProduct.getName());
          product.setPrice(newProduct.getPrice());
          product.setDescription(newProduct.getDescription());
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

  public ResponseEntity<?> updatePart(Long productId, Long partId, Part newPart) {

    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new ProductNotFoundException(productId));

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

    EntityModel<Part> entityModel = partAssembler.toModel(partRepository.save(updatedPart));

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
  }

}
