package mercury.procurems.interfaces.rest;

import mercury.procurems.application.internal.commandservices.SupplierProcurementCommandService;
import mercury.procurems.application.internal.queryservices.SupplierProcurementQueryService;
import mercury.procurems.domain.aggregate.Supplier;
import mercury.procurems.domain.entity.Contact;

import java.util.Set;

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

@GetMapping("/supplierProcurement/{id}/contacts")
@ResponseBody
public Set<Contact> getFoos(@PathVariable Long id) {
    Set<Contact> name = queryService.findById(id).getContacts();
    return name;
}

@PutMapping("/supplierProcurement/{id}")
ResponseEntity<?> updateSupplierContact(@RequestBody Supplier supplier, @PathVariable Long id ) {
    return commandService.updateSupplier(supplier, id);
}

@PutMapping("/supplierProcurement/{id}/contact")
ResponseEntity<?> updateSupplierContact(@PathVariable Long id, @RequestBody Contact contact) {
    return commandService.addSupplierContact(id, contact);
}

@DeleteMapping("/supplierProcurement/{id}")
String delete(@PathVariable Long id){
    return commandService.deleteSupplier(id);
}

}


    