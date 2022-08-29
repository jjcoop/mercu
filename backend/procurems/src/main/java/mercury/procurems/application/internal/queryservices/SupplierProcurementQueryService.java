package mercury.procurems.application.internal.queryservices;

import mercury.procurems.interfaces.rest.SupplierController;
import mercury.procurems.interfaces.rest.transform.SupplierModelAssembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import mercury.procurems.domain.aggregate.Supplier;
import mercury.procurems.infrastructure.repository.SupplierRepository;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class SupplierProcurementQueryService {
    private SupplierRepository supplierRepository;
    private SupplierModelAssembler assembler;

    public CollectionModel<EntityModel<Supplier>> all() {
    
      List<EntityModel<Supplier>> suppliers = supplierRepository.findAll().stream()
          .map(assembler::toModel)
          .collect(Collectors.toList());
    
      return CollectionModel.of(suppliers, linkTo(methodOn(SupplierController.class).allSuppliers()).withSelfRel());
    }

    public Supplier findById(Long id){
        return supplierRepository.findById(id)
        .orElseThrow(() -> new SupplierNotFoundException(id));
    }
}
