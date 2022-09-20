package mercury.inventoryms.interfaces.rest.transform;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import mercury.inventoryms.domain.aggregate.Part;
import mercury.inventoryms.interfaces.rest.ProductController;

// use this default constructor, when autowired, use that constructor instead.
@Component // tells the app there is something you need to pay attention to.
public
class PartModelAssembler implements RepresentationModelAssembler<Part, EntityModel<Part>> {

    @Override
    public EntityModel<Part> toModel(Part entity) {
        return EntityModel.of(entity, //
        linkTo(methodOn(ProductController.class).getPart(entity.getId())).withSelfRel(),
        linkTo(methodOn(ProductController.class).getParts()).withRel("parts"));
    }
}