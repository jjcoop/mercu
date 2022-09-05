package mercury.inventoryms.application.internal.commandservices;

import mercury.inventoryms.interfaces.rest.transform.SupplierModelAssembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import mercury.inventoryms.domain.aggregate.Part;
import mercury.inventoryms.domain.entity.Product;
import mercury.inventoryms.infrastructure.repository.PartRepository;
import mercury.inventoryms.application.internal.queryservices.PartNotFoundException;


@Service
public class PartInventoryCommandService {
    private PartRepository partRepository;
    private PartModelAssembler assembler;

    public PartInventoryCommandService(PartRepository partRepository, PartModelAssembler assembler) {
        this.partRepository = partRepository;
        this.assembler = assembler;
    }

    public ResponseEntity<?> addPart(Part newPart) {
  
      EntityModel<Part> entityModel = assembler.toModel(partRepository.save(newPart));
    
      return ResponseEntity
          .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
          .body(entityModel);
    }

    public Part replacePart(Part newPart, long id){
        Part updatedPart = partRepository.findById(id) //
        .map(part -> {
          part.setPartName(newPart.getPartName());
          part.setDescription(newPart.getDescription());
          return partRepository.save(part);
        }) //
        .orElseGet(() -> {
          newPart.setId(id);
          return partRepository.save(newPart);
        });

        return updatedPart;
    }

    public ResponseEntity<?> addPartProduct(Long id, Product product) {
      
      Part part = partRepository.findById(id)
          .orElseThrow(() -> new PartNotFoundException(id));
  
      //TODO ask jacob about the relationship (adding a product to multiple partsd)
      product.setSupplier(part);
      part.addPartProduct(product);
      partRepository.save(part);
  
      EntityModel<Part> entityModel = assembler.toModel(part);
  
      return ResponseEntity 
          .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
          .body(entityModel);
    }      
    
    public ResponseEntity<?> updatePart(Part newPart, Long id) {
      
      Part updatedPart = partRepository.findById(id) //
      .map(part -> {
        part.setPartName(newPart.getPartName());
        part.setDescription(newPart.getDescription());
        return partRepository.save(part);
      }) //
      .orElseGet(() -> {
        newPart.setId(id);
        return partRepository.save(newPart);
      });
  
      EntityModel<Part> entityModel = assembler.toModel(updatedPart);
  
      return ResponseEntity //
          .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
          .body(entityModel);
    }

}
