package mercury.salems.application.internal.queryservices;

import mercury.salems.antiCorruptionLayer.sharedModel.ProductTemplate;
import mercury.salems.domain.aggregate.InStoreSale;
import mercury.salems.domain.aggregate.OnlineSale;
import mercury.salems.domain.aggregate.Sale;
import mercury.salems.domain.entity.Store;
import mercury.salems.infrastructure.repository.SaleRepository;
import mercury.salems.infrastructure.repository.StoreRepository;
import mercury.salems.interfaces.rest.SaleController;
import mercury.salems.interfaces.rest.transform.SaleModelAssembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class SaleQueryService {
    @Autowired
    private final SaleRepository<OnlineSale> onlineSaleRepository;
    @Autowired
    private final SaleRepository<InStoreSale> inStoreSaleRepository;
    @Autowired
    private final StoreRepository storeRepository;
    @Autowired

    private SaleModelAssembler assembler;

    // ---------------------
    // Sale
    // ---------------------
    public SaleQueryService(SaleRepository<OnlineSale> onlineSaleRepository,
            SaleRepository<InStoreSale> inStoreSaleRepository, StoreRepository storeRepository,
            SaleModelAssembler assembler) {
        this.onlineSaleRepository = onlineSaleRepository;
        this.inStoreSaleRepository = inStoreSaleRepository;
        this.storeRepository = storeRepository;
        this.assembler = assembler;
    }

    public CollectionModel<EntityModel<Sale>> all() {
        List<EntityModel<Sale>> sales = (List<EntityModel<Sale>>) onlineSaleRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(sales, linkTo(methodOn(SaleController.class).all()).withSelfRel());
    }

    public Sale findById(Long id) {
        return onlineSaleRepository.findById(id)
                .orElseThrow(() -> new SaleNotFoundException(id));
    }

    public ProductTemplate getProductByOnlineSaleId(Long saleId) {
        OnlineSale sale = (OnlineSale) onlineSaleRepository.findById(saleId)
                .orElseThrow(() -> new SaleNotFoundException(saleId));
        
        // TODO get product info from product repo via REST


        // TODO remove this
        return null;
    }

    // ---------------------
    // OnlineSale
    // ---------------------
    public CollectionModel<EntityModel<OnlineSale>> allOnlineSales() {
        List<EntityModel<OnlineSale>> sales = (List<EntityModel<OnlineSale>>) onlineSaleRepository.findAllOnlineSales()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(sales, linkTo(methodOn(SaleController.class).all()).withSelfRel());
    }

    public EntityModel<OnlineSale> findOnlineSaleById(Long id) {
        OnlineSale sale = (OnlineSale) onlineSaleRepository.findById(id)
                .orElseThrow(() -> new SaleNotFoundException(id));

        return assembler.toModel(sale);
    }

    // ---------------------
    // InStoreSale
    // ---------------------
    public CollectionModel<EntityModel<InStoreSale>> allInStoreSales() {
        List<EntityModel<InStoreSale>> sales = (List<EntityModel<InStoreSale>>) inStoreSaleRepository
                .findAllInStoreSales().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(sales, linkTo(methodOn(SaleController.class).all()).withSelfRel());
    }

    public EntityModel<InStoreSale> findInStoreSaleById(Long id) {
        InStoreSale sale = (InStoreSale) inStoreSaleRepository.findById(id)
                .orElseThrow(() -> new SaleNotFoundException(id));

        return assembler.toModel(sale);
    }

    // ---------------------
    // Stores
    // ---------------------
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
