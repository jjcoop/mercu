package mercury.procurems.application.internal.commandservices;

import mercury.procurems.interfaces.rest.transform.SupplierModelAssembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import mercury.procurems.domain.aggregate.Supplier;
import mercury.procurems.domain.entity.Contact;
import mercury.procurems.infrastructure.repository.SupplierRepository;
import mercury.procurems.application.internal.queryservices.SupplierNotFoundException;

@Service
public class SupplierProcurementCommandService {
  private SupplierRepository supplierRepository;
  private SupplierModelAssembler assembler;

  public SupplierProcurementCommandService(SupplierRepository supplierRepository, SupplierModelAssembler assembler) {
    this.supplierRepository = supplierRepository;
    this.assembler = assembler;
  }

  // **********************************************************************
  //                            ADD SUPPLIER
  // **********************************************************************
  public ResponseEntity<?> addSupplier(Supplier newSupplier) {

    EntityModel<Supplier> entityModel = assembler.toModel(supplierRepository.save(newSupplier));
    System.out.println("**** SUPPLIER ADDED ****");
    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  // **********************************************************************
  //                        ADD SUPPLIER CONTACTS
  // **********************************************************************
  public ResponseEntity<?> addSupplierContact(Long id, Contact contact) {

    Supplier supplier = supplierRepository.findById(id)
        .orElseThrow(() -> new SupplierNotFoundException(id));

    contact.setSupplier(supplier);
    supplier.addContact(contact);
    supplierRepository.save(supplier);
    System.out.println("**** SUPPLIER CONTACT ADDED ****");


    EntityModel<Supplier> entityModel = assembler.toModel(supplier);

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
  }

  // **********************************************************************
  //                              UPDATE SUPPLIER
  // **********************************************************************
  public ResponseEntity<?> updateSupplier(Supplier newSupplier, Long id) {

    Supplier updatedSupplier = supplierRepository.findById(id) //
        .map(supplier -> {
          supplier.setCompanyName(newSupplier.getCompanyName());
          supplier.setBase(newSupplier.getBase());
          return supplierRepository.save(supplier);
        }) //
        .orElseGet(() -> {
          newSupplier.setId(id);
          return supplierRepository.save(newSupplier);
        });
    System.out.println("**** SUPPLIER UPDATED ****");
    EntityModel<Supplier> entityModel = assembler.toModel(updatedSupplier);

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
  }

  // **********************************************************************
  //                      UPDATE SUPPLIER CONTACT
  // **********************************************************************
  public ResponseEntity<?> updateSupplierContact(Contact contact, Long supplierID, Long contactID) {

    Supplier supplier = supplierRepository.findById(supplierID) //
        .orElseThrow(() -> new SupplierNotFoundException(supplierID));

    supplier.updateContact(contactID, contact);
    supplierRepository.save(supplier);

    System.out.println("**** SUPPLIER CONTACT UPDATED ****");
    EntityModel<Supplier> entityModel = assembler.toModel(supplier);

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
  }

  // **********************************************************************
  //            CASCADE DELETE SUPPLIER & SUPPLIERS CONTACTS
  // **********************************************************************
  public String deleteSupplier(Long id) {
    Supplier supplier = supplierRepository.findById(id)
        .orElseThrow(() -> new SupplierNotFoundException(id));
    supplierRepository.delete(supplier);
    System.out.println("**** SUPPLIER DELETED ****");


    return supplier.getCompanyName() + " REMOVED";
  }

}
