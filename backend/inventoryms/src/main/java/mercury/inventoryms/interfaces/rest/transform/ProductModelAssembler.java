package mercury.inventoryms.interfaces.rest.transform;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;



import mercury.inventoryms.domain.aggregate.Product;
import mercury.inventoryms.interfaces.rest.ProductController;

// use this default constructor, when autowired, use that constructor instead.
@Component // tells the app there is something you need to pay attention to.
public
class ProductModelAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>> {

    @Override
    public EntityModel<Product> toModel(Product entity) {
        return EntityModel.of(entity, //
        linkTo(methodOn(ProductController.class).one(entity.getId())).withSelfRel(),
        linkTo(methodOn(ProductController.class).all()).withRel("products"));
    }
}