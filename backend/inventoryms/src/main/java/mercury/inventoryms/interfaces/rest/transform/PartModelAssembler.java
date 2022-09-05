package mercury.inventoryms.interfaces.rest.transform;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;



import mercury.inventoryms.domain.aggregate.Part;
import mercury.inventoryms.interfaces.rest.PartController;

// use this default constructor, when autowired, use that constructor instead.
@Component // tells the app there is something you need to pay attention to.
public
class PartModelAssembler implements RepresentationModelAssembler<Part, EntityModel<Part>> {

    //TODO ask jacob about here
    @Override
    public EntityModel<Part> toModel(Part entity) {
        return EntityModel.of(entity, //
        linkTo(methodOn(PartController.class).one(entity.getId())).withSelfRel(),
        linkTo(methodOn(PartController.class).all()).withRel("product"));
    }
}