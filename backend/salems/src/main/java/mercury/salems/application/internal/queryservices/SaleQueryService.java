package mercury.salems.application.internal.queryservices;

import mercury.salems.domain.aggregate.Sale;
import mercury.salems.domain.entity.Store;
import mercury.salems.infrastructure.repository.SaleRepository;
import mercury.salems.infrastructure.repository.StoreRepository;

import mercury.salems.interfaces.rest.SaleController;
import mercury.salems.interfaces.rest.transform.SaleModelAssembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class SaleQueryService {
    private final SaleRepository saleRepository;
    private final StoreRepository storeRepository;
    private SaleModelAssembler assembler;

    public SaleQueryService(SaleRepository saleRepository, StoreRepository storeRepository,
            SaleModelAssembler assembler) {
        this.saleRepository = saleRepository;
        this.storeRepository = storeRepository;
        this.assembler = assembler;
    }

    public CollectionModel<EntityModel<Sale>> all() {

        List<EntityModel<Sale>> sales = (List<EntityModel<Sale>>) saleRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(sales, linkTo(methodOn(SaleController.class).all()).withSelfRel());
    }

    public Sale findById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new SaleNotFoundException(id));
    }

    public CollectionModel<EntityModel<Store>> allStores() {

        List<EntityModel<Store>> stores = (List<EntityModel<Store>>) storeRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(stores, linkTo(methodOn(SaleController.class).all()).withSelfRel());
    }

    public Store findStoreById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new StoreNotFoundException(id));
    }
}
