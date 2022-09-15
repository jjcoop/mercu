package mercury.inventoryms.application.internal.commandservices;

import mercury.inventoryms.interfaces.rest.transform.ProductModelAssembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import mercury.inventoryms.domain.aggregate.Product;
import mercury.inventoryms.domain.entity.Part;
import mercury.inventoryms.infrastructure.repository.ProductRepository;
import mercury.inventoryms.application.internal.queryservices.ProductNotFoundException;


@Service
public class ProductInventoryCommandService {
    private ProductRepository productRepository;
    private ProductModelAssembler assembler;

    public ProductInventoryCommandService(ProductRepository productRepository, ProductModelAssembler assembler) {
        this.productRepository = productRepository;
        this.assembler = assembler;
    }

    public ResponseEntity<?> addProduct(Product newProduct) {
  
      EntityModel<Product> entityModel = assembler.toModel(productRepository.save(newProduct));
    
      return ResponseEntity
          .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
          .body(entityModel);
    }

    public Product replaceProduct(Product newProduct, long id){
      Product updatedProduct = productRepository.findById(id) //
      .map(product -> {
        product.setName(newProduct.getName());
        product.setDescription(newProduct.getDescription());
        return productRepository.save(product);
      }) //
      .orElseGet(() -> {
        newProduct.setId(id);
        return productRepository.save(newProduct);
      });

        return updatedProduct;
    }

    public ResponseEntity<?> addProductPart(Long id, Part part) {
      
      Product product = productRepository.findById(id)
          .orElseThrow(() -> new ProductNotFoundException(id));
  
      part.setProduct(product);
      product.addPart(part);
      productRepository.save(product);
  
      EntityModel<Product> entityModel = assembler.toModel(product);
  
      return ResponseEntity //
          .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
          .body(entityModel);
    }      
    
    public ResponseEntity<?> updateProduct( Product newProduct, Long id) {
      
      Product updatedProduct = productRepository.findById(id) //
      .map(product -> {
        product.setName(newProduct.getName());
        product.setDescription(newProduct.getDescription());
        return productRepository.save(product);
      }) //
      .orElseGet(() -> {
        newProduct.setId(id);
        return productRepository.save(newProduct);
      });
  
      EntityModel<Product> entityModel = assembler.toModel(productRepository.save(updatedProduct));
  
      return ResponseEntity //
          .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
          .body(entityModel);
    }

}
