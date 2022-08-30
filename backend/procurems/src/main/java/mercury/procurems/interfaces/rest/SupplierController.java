package mercury.procurems.interfaces.rest;

import mercury.procurems.application.internal.commandservices.SupplierProcurementCommandService;
import mercury.procurems.application.internal.queryservices.SupplierProcurementQueryService;
import mercury.procurems.domain.aggregate.Supplier;
import mercury.procurems.domain.entity.Contact;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController    // This means that this class is a Controller
public class SupplierController {
   private final SupplierProcurementCommandService commandService; //application service dependency
   private final SupplierProcurementQueryService queryService;


public SupplierController(SupplierProcurementCommandService commandService, SupplierProcurementQueryService queryService) {
    this.commandService = commandService;
    this.queryService = queryService;
}

@PostMapping("/supplierProcurement")
ResponseEntity<?> newSupplier(@RequestBody Supplier supplier){
    System.out.println("**** Supplier Added ****");
    return commandService.addSupplier(supplier);
}


@GetMapping("/supplierProcurement")
public CollectionModel<EntityModel<Supplier>> all(){
    return queryService.all();
}

@GetMapping("/supplierProcurement/{id}")
public Supplier one(@PathVariable Long id){
    return queryService.findById(id);
}
   
@PutMapping("/suppliers/{id}/contact")
ResponseEntity<?> updateSupplierContact(@PathVariable Long id, @RequestBody Contact contact) {
    return commandService.addSupplierContact(id, contact);
}  
}


    