package mercury.salems.application.internal.commandservices;

import java.util.Date;

import mercury.salems.application.internal.outboundservices.OrderingService;
import mercury.salems.application.internal.outboundservices.acl.ExternalOrderSaleService;
import mercury.salems.application.internal.queryservices.StoreNotFoundException;
import mercury.salems.domain.aggregate.InStoreSale;
import mercury.salems.domain.aggregate.OnlineSale;
import mercury.salems.domain.entity.Store;
import mercury.salems.infrastructure.repository.SaleRepository;
import mercury.salems.infrastructure.repository.StoreRepository;
import mercury.salems.interfaces.rest.transform.SaleModelAssembler;
import mercury.shareDomain.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SaleCommandService {

  @Autowired
  private SaleRepository<OnlineSale> onlineSaleRepository;

  @Autowired
  private SaleRepository<InStoreSale> inStoreSaleRepository;

  private StoreRepository storeRepository;
  private SaleModelAssembler assembler;
  private OrderingService orderingService;
  private ExternalOrderSaleService externalOrderSaleService;

  public SaleCommandService(
      SaleRepository<OnlineSale> onlineSaleRepository,
      SaleRepository<InStoreSale> inStoreSaleRepository,
      StoreRepository storeRepository,
      SaleModelAssembler assembler,
      OrderingService orderingService,
      ExternalOrderSaleService externalOrderSaleService) {
    this.onlineSaleRepository = onlineSaleRepository;
    this.inStoreSaleRepository = inStoreSaleRepository;
    this.storeRepository = storeRepository;
    this.assembler = assembler;
    this.orderingService = orderingService;
    this.externalOrderSaleService = externalOrderSaleService;
  }

  // **********************************************************************
  //                               ADD STORE
  // **********************************************************************
  public ResponseEntity<?> addStore(Store newStore) {
    EntityModel<Store> entityModel = assembler.toModel(
        storeRepository.save(newStore));
    
    System.out.println("**** STORE ADDED ****");
    

    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  // **********************************************************************
  //                            ADD STORE SALE
  // **********************************************************************
  public ResponseEntity<?> addInStoreSale(Long id, InStoreSale newSale) {
    Date date = new Date();
    newSale.setDateTime(date);

    Order newOrder = new Order(newSale.getId(), "PENDING", newSale.getProductName(), newSale.getQuantity());
    Order returnOrder = orderingService.send(newOrder);

    newSale = externalOrderSaleService.processOrder(returnOrder, newSale);

    System.out.println("**** ONLINE SALE ADDED ****");
    System.out.println("**** ORDER " + returnOrder.getStatusCode() + " ****");

    Store store = storeRepository
        .findById(id)
        .orElseThrow(() -> new StoreNotFoundException(id));
    newSale.setStore(store);
    store.addSale(newSale);

    EntityModel<InStoreSale> entityModel = assembler.toModel(inStoreSaleRepository.save(newSale));
    System.out.println("**** STORE SALE ADDED ****");


    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  // **********************************************************************
  //                            ADD ONLINE SALE
  // **********************************************************************
  public ResponseEntity<?> addOnlineSale(OnlineSale newSale) {
    Date date = new Date();
    newSale.setDateTime(date);

    Order newOrder = new Order(newSale.getId(), "PENDING", newSale.getProductName(), newSale.getQuantity());
    Order returnOrder = orderingService.send(newOrder);

    newSale = externalOrderSaleService.processOrder(returnOrder, newSale);

    EntityModel<OnlineSale> entityModel = assembler.toModel(onlineSaleRepository.save(newSale));
    System.out.println("**** ONLINE SALE ADDED ****");
    System.out.println("**** ORDER " + returnOrder.getStatusCode() + " ****");


    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

}
