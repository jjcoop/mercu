package mercury.procurems.interfaces.rest;

import mercury.procurems.application.internal.commandservices.SupplierProcurementCommandService;
import mercury.procurems.application.internal.queryservices.SupplierProcurementQueryService;
import mercury.procurems.domain.aggregate.Supplier;
import mercury.procurems.domain.entity.Contact;

import java.net.URI;
import java.util.Set;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
public class SupplierController {
    private final SupplierProcurementCommandService commandService;
    private final SupplierProcurementQueryService queryService;

    public SupplierController(SupplierProcurementCommandService commandService,
            SupplierProcurementQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping("/supplierProcurement")
    ResponseEntity<?> newSupplier(@RequestBody Supplier supplier) {
        System.out.println("**** Supplier Added ****");
        return commandService.addSupplier(supplier);
    }

    @GetMapping("/supplierProcurement")
    public CollectionModel<EntityModel<Supplier>> all() {
        return queryService.all();
    }

    @GetMapping("/supplierProcurement/{id}")
    public Supplier one(@PathVariable Long id) {
        return queryService.findById(id);
    }

    @GetMapping("/supplierProcurement/{id}/contacts")
    @ResponseBody
    public Set<Contact> getContacts(@PathVariable Long id) {
        Set<Contact> name = queryService.findById(id).getContacts();
        return name;
    }

    @PutMapping("/supplierProcurement/{id}")
    ResponseEntity<?> updateSupplier(@RequestBody Supplier supplier, @PathVariable Long id) {
        return commandService.updateSupplier(supplier, id);
    }

    @PutMapping("/supplierProcurement/{id}/contact")
    ResponseEntity<?> addSupplierContact(@PathVariable Long id, @RequestBody Contact contact) {
        return commandService.addSupplierContact(id, contact);
    }

    @PutMapping("/supplierProcurement/{id}/contact/{contactId}")
    ResponseEntity<?> updateSupplierContact(@PathVariable Long id, @PathVariable Long contactId,
            @RequestBody Contact contact) {
        return commandService.updateSupplierContact(contact, id, contactId);
    }

    @DeleteMapping("/supplierProcurement/{id}")
    String delete(@PathVariable Long id) {
        return commandService.deleteSupplier(id);
    }

    @GetMapping("/supplierProcurement/lookup/")
    @ResponseBody
    public URI lookup(@RequestParam String name) {
        return queryService.findByName(name);
    }
}
