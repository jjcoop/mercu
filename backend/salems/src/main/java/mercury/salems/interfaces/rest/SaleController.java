package mercury.salems.interfaces.rest;

import mercury.salems.application.internal.commandservices.SaleCommandService;
import mercury.salems.application.internal.queryservices.SaleQueryService;
import mercury.salems.domain.aggregate.InStoreSale;
import mercury.salems.domain.aggregate.OnlineSale;
import mercury.salems.domain.aggregate.Sale;
import mercury.salems.domain.entity.Store;
import mercury.shareDomain.ProductTemplate;
import mercury.shareDomain.PartTemplate;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController    // This means that this class is a Controller
public class SaleController {
   private final SaleCommandService commandService; //application service dependency
   private final SaleQueryService queryService;


public SaleController(SaleCommandService commandService, SaleQueryService queryService) {
    this.commandService = commandService;
    this.queryService = queryService;
}

// Sale
@PostMapping("/sales")
ResponseEntity<?> newSale(@RequestBody OnlineSale sale){
    System.out.println("**** Sale Added ****");
    return commandService.addSale(sale);
}
// @PostMapping("/sales/store/{id}")
// ResponseEntity<?> updateStoreSale(@PathVariable Long id, @RequestBody InStoreSale sale) {
//     return commandService.addStoreSale(id, sale);
// }

@GetMapping("/sales")
public CollectionModel<EntityModel<Sale>> all(){
    return queryService.all();
}
@GetMapping("/sales/{id}")
public Sale one(@PathVariable Long id){
    return queryService.findById(id);
}
@PutMapping("/sales/{id}/")
ResponseEntity<?> replaceSale(@RequestBody Sale sale, @PathVariable Long id ) {
    return commandService.updateSale(sale, id);
}

// Online sale
@PostMapping("/sales/online")
ResponseEntity<?> newOnlineSale(@RequestBody OnlineSale sale){
    System.out.println("**** Online Sale Added ****");
    return commandService.addOnlineSale(sale);
}
@GetMapping("/sales/online")
public CollectionModel<EntityModel<OnlineSale>> allOnlineSales(){
    return queryService.allOnlineSales();
}
@GetMapping("/sales/online/{id}")
public EntityModel<OnlineSale> oneOnlineSale(@PathVariable Long id){
    return queryService.findOnlineSaleById(id);
}
@GetMapping("/sales/online/{id}/product")
public ProductTemplate getProductByOnlineSaleId(@PathVariable Long id){
    return queryService.getProductByOnlineSaleId(id);
}

// InStoreSale
@PostMapping("/sales/store/{storeId}")
ResponseEntity<?> addInStoreSale(@PathVariable Long storeId, @RequestBody InStoreSale sale) {
    System.out.println("**** InStore Sale Added ****");
    return commandService.addInStoreSale(storeId, sale);
}
@GetMapping("/sales/store/{id}/product")
public ProductTemplate getProductByInStoreSaleId(@PathVariable Long id){
    return queryService.getProductByInStoreSaleId(id);
}

// Store
@PostMapping("/sales/store")
ResponseEntity<?> newStore(@RequestBody Store store){
    System.out.println("**** Store Added ****");
    return commandService.addStore(store);
}
@GetMapping("/sales/store")
public CollectionModel<EntityModel<Store>> allStores(){
    return queryService.allStores();
}
@GetMapping("/sales/store/{id}")
public Store getStore(@PathVariable Long id){
    return queryService.findStoreById(id);
}

}


    