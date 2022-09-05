package mercury.inventoryms.application.internal.queryservices;


import mercury.inventoryms.interfaces.rest.PartController;
import mercury.inventoryms.interfaces.rest.transform.PartModelAssembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import mercury.inventoryms.domain.aggregate.Part;
import mercury.inventoryms.infrastructure.repository.PartRepository;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


//SupplierProcurementQueryService
@Service
public class PartInventoryQueryService {
    private final PartRepository partRepository;
    private PartModelAssembler assembler;

    

    public PartInventoryQueryService(PartRepository partRepository, PartModelAssembler assembler) {
        this.partRepository = partRepository;
        this.assembler = assembler;
    }

    public CollectionModel<EntityModel<Part>> all() {
    
      List<EntityModel<Part>> parts = (List<EntityModel<Part>>) partRepository.findAll().stream()
          .map(assembler::toModel)
          .collect(Collectors.toList());
    
      return CollectionModel.of(parts, linkTo(methodOn(PartController.class).all()).withSelfRel());
    }

    public Part findById(Long id){
        return partRepository.findById(id)
        .orElseThrow(() -> new SupplierNotFoundException(id));
    }
}
