package mercury.inventoryms.interfaces.rest;


//TODO update imports
import mercury.inventoryms.application.internal.commandservices.PartInventoryCommandService;
import mercury.inventoryms.application.internal.queryservices.PartInventoryQueryService;
import mercury.inventoryms.domain.aggregate.Part;
import mercury.inventoryms.domain.entity.Product;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController    // This means that this class is a Controller
public class PartController {
    private final PartInventoryCommandService commandService;
    private final PartInventoryQueryService queryService;


public PartController(PartInventoryCommandService commandService, PartInventoryQueryService queryService) {
    this.commandService = commandService;
    this.queryService = queryService;
}

@PostMapping("/partInventory")
ResponseEntity<?> newPart(@RequestBody Part part){
    System.out.println("**** Part Added ****");
    return commandService.addPart(part);
}


@GetMapping("/partInventory")
public CollectionModel<EntityModel<Part>> all(){
    return queryService.all();
}

@GetMapping("/partInventory/{id}")
public Part one(@PathVariable Long id){
    return queryService.findById(id);
}


@PutMapping("/partInventory/{id}")
ResponseEntity<?> updatePart(@RequestBody Part part, @PathVariable Long id ) {
    return commandService.updatePart(part, id);
}


//TODO ask jacob about this one
//do i need to flip around part and product to make product an aggregate and part an entity
//do i need a product controller instead of a part controller
/* 
@PutMapping("/partInventory/{id}/product")
ResponseEntity<?> updateSupplierContact(@PathVariable Long id, @RequestBody Contact contact) {
    return commandService.addSupplierContact(id, contact);
}  
*/
}


    