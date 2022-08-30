package mercury.procurems.interfaces.rest.transform;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;



import mercury.procurems.domain.aggregate.Supplier;
import mercury.procurems.interfaces.rest.SupplierController;

// use this default constructor, when autowired, use that constructor instead.
@Component // tells the app there is something you need to pay attention to.
public
class SupplierModelAssembler implements RepresentationModelAssembler<Supplier, EntityModel<Supplier>> {

    @Override
    public EntityModel<Supplier> toModel(Supplier entity) {
        return EntityModel.of(entity, //
        linkTo(methodOn(SupplierController.class).one(entity.getId())).withSelfRel(),
        linkTo(methodOn(SupplierController.class).all()).withRel("suppliers"));
    }
}