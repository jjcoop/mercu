package mercury.inventoryms.interfaces.rest;

import mercury.inventoryms.application.internal.commandservices.ProductInventoryCommandService;
import mercury.inventoryms.application.internal.queryservices.ProductInventoryQueryService;
import mercury.inventoryms.domain.aggregate.Product;
import mercury.inventoryms.domain.entity.Part;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController    // This means that this class is a Controller
public class ProductController {
   private final ProductInventoryCommandService commandService; //application service dependency
   private final ProductInventoryQueryService queryService;


public ProductController(ProductInventoryCommandService commandService, ProductInventoryQueryService queryService) {
    this.commandService = commandService;
    this.queryService = queryService;
}

@PostMapping("/productInventory")
ResponseEntity<?> addProduct(@RequestBody Product product){
    System.out.println("**** Product Added ****");
    return commandService.addProduct(product);
}

// @PostMapping("/productInventory/check")
// Product createSale(@RequestBody Product product){
//     System.out.println("**** Checking Inventory ****");
//     return queryService.checkProductStock(product);
// }
// @PostMapping("/productInventory/part/check")
// Part createSale(@RequestBody Part part){
//     System.out.println("**** Checking Inventory ****");
//     return queryService.checkPartStock(part);
// }

@GetMapping("/productInventory")
public CollectionModel<EntityModel<Product>> getProducts(){
    return queryService.all();
}

@GetMapping("/productInventory/{id}")
public Product getProduct(@PathVariable Long id){
    return queryService.findById(id);
}

@PutMapping("/productInventory/{id}")
ResponseEntity<?> updateProduct(@RequestBody Product product, @PathVariable Long id ) {
    return commandService.updateProduct(product, id);
}

@PutMapping("/productInventory/{id}/part")
ResponseEntity<?> addProductPart(@PathVariable Long id, @RequestBody Part part) {
    return commandService.addProductPart(id, part);
}

@PutMapping("/productInventory/{productId}/part/{partId}")
ResponseEntity<?> updateProductPart(@PathVariable Long productId, @PathVariable Long partId, @RequestBody Part part) {
    return commandService.updatePart(productId, partId, part);
}

@GetMapping("/productInventory/sales")
public String soldProducts(){
    return queryService.findByProductName();
}

@GetMapping("/productInventory/parts/{id}")
public Part getPart(@PathVariable Long id){
    return queryService.findPartById(id);
}

@GetMapping("/productInventory/parts")
public CollectionModel<EntityModel<Part>> getParts(){
    return queryService.allParts();
}

}
    