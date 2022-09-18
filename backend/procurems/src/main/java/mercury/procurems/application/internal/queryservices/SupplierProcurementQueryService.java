package mercury.procurems.application.internal.queryservices;

import mercury.procurems.interfaces.rest.SupplierController;
import mercury.procurems.interfaces.rest.transform.SupplierModelAssembler;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import mercury.procurems.domain.aggregate.Supplier;
import mercury.procurems.infrastructure.repository.SupplierRepository;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class SupplierProcurementQueryService {
    private final SupplierRepository supplierRepository;
    private SupplierModelAssembler assembler;

    

    public SupplierProcurementQueryService(SupplierRepository supplierRepository, SupplierModelAssembler assembler) {
        this.supplierRepository = supplierRepository;
        this.assembler = assembler;
    }

    public CollectionModel<EntityModel<Supplier>> all() {
    
      List<EntityModel<Supplier>> suppliers = (List<EntityModel<Supplier>>) supplierRepository.findAll().stream()
          .map(assembler::toModel)
          .collect(Collectors.toList());
    
      return CollectionModel.of(suppliers, linkTo(methodOn(SupplierController.class).all()).withSelfRel());
    }

    public Supplier findById(Long id){
        return supplierRepository.findById(id)
        .orElseThrow(() -> new SupplierNotFoundException(id));
    }

    public URI findByName(String name){
        List<Supplier> suppliers = supplierRepository.findAll();
        Supplier tmpSupplier = new Supplier();
        String searchName = URLDecoder.decode(name);//, StandardCharsets.UTF_8);
        System.out.println(searchName);
        for (Supplier s : suppliers){
            if (s.getCompanyName().equals(searchName)){
                tmpSupplier = s;
            }
        }
        try {
            return linkTo(methodOn(SupplierController.class).one(tmpSupplier.getId())).toUri();
        } catch (Exception e) {
            return URI.create("error:supplierMS");
        }
        
    }

}
