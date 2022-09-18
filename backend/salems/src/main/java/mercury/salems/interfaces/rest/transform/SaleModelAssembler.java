package mercury.salems.interfaces.rest.transform;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import mercury.salems.domain.aggregate.InStoreSale;
import mercury.salems.domain.aggregate.OnlineSale;
import mercury.salems.domain.aggregate.Sale;
import mercury.salems.domain.entity.Store;
import mercury.salems.interfaces.rest.SaleController;

// use this default constructor, when autowired, use that constructor instead.
@Component // tells the app there is something you need to pay attention to.
public
class SaleModelAssembler implements RepresentationModelAssembler<Sale, EntityModel<Sale>> {

    @Override
    public EntityModel<Sale> toModel(Sale entity) {
        return EntityModel.of(entity, //
        linkTo(methodOn(SaleController.class).one(entity.getId())).withSelfRel(),
        linkTo(methodOn(SaleController.class).all()).withRel("Sales"));
    }

    public EntityModel<Store> toModel(Store entity) {
        return EntityModel.of(entity, //
        linkTo(methodOn(SaleController.class).getStore(entity.getId())).withSelfRel(),
        linkTo(methodOn(SaleController.class).allStores()).withRel("Stores"));
    }

    public EntityModel<OnlineSale> toModel(OnlineSale entity) {
        return EntityModel.of(entity, //
        linkTo(methodOn(SaleController.class).oneOnlineSale(entity.getId())).withSelfRel(),
        linkTo(methodOn(SaleController.class).allOnlineSales()).withRel("Sales"));
    }

    public EntityModel<InStoreSale> toModel(InStoreSale entity) {
        return EntityModel.of(entity, //
        linkTo(methodOn(SaleController.class).one(entity.getId())).withSelfRel(),
        linkTo(methodOn(SaleController.class).all()).withRel("Sales"));
    }
    
}