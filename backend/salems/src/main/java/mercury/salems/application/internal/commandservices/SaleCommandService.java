package mercury.salems.application.internal.commandservices;

import mercury.salems.interfaces.rest.transform.SaleModelAssembler;

import java.util.Date;
import java.util.Map;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mercury.salems.domain.aggregate.Sale;
import mercury.salems.antiCorruptionLayer.sharedModel.Product;
import mercury.salems.application.internal.queryservices.SaleNotFoundException;
import mercury.salems.domain.aggregate.InStoreSale;
import mercury.salems.domain.aggregate.OnlineSale;
import mercury.salems.domain.entity.Store;
import mercury.salems.infrastructure.repository.SaleRepository;
import mercury.salems.infrastructure.repository.StoreRepository;

@Service
public class SaleCommandService {
  private SaleRepository saleRepository;
  private StoreRepository storeRepository;
  private SaleModelAssembler assembler;
  private RestTemplate restTemplate;
  private Map<Integer, Sale> productBackOrder;

  public SaleCommandService(SaleRepository saleRepository, StoreRepository storeRepository,
      SaleModelAssembler assembler, RestTemplate restTemplate, Map<Integer, Sale> productBackOrder) {
    this.saleRepository = saleRepository;
    this.storeRepository = storeRepository;
    this.assembler = assembler;
    this.restTemplate = restTemplate;
    this.productBackOrder = productBackOrder;
  }

  public ResponseEntity<?> addSale(OnlineSale newSale) {

    Date date = new Date();
    newSale.setDateTime(date);
    EntityModel<OnlineSale> entityModel = assembler.toModel(saleRepository.save(newSale));

    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  public ResponseEntity<?> addStore(Store newStore) {

    EntityModel<Store> entityModel = assembler.toModel(storeRepository.save(newStore));

    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  public Sale replaceSale(Sale newSale, long id) {
    Sale updatedSale = saleRepository.findById(id) //
        .map(sale -> {
          sale.setProductName(newSale.getProductName());
          sale.setDateTime(newSale.getDateTime());
          return saleRepository.save(sale);
        }) //
        .orElseGet(() -> {
          newSale.setId(id);
          return saleRepository.save(newSale);
        });

    return updatedSale;
  }

  public ResponseEntity<?> addStoreSale(Long id, InStoreSale newSale) {
    InStoreSale inStoreSale = newSale;
    Store store = storeRepository.findById(id)
        .orElseThrow(() -> new SaleNotFoundException(id));
    String rootUrl = "http://localhost:8788/productInventory/check";
    Product product = new Product();
    product.setName(newSale.getProductName());
    product.setDescription("TMP");
    product.setPrice(0);
    product = restTemplate.postForObject(rootUrl, product, Product.class);

    if (product != null) {
      System.out.println("****PRODUCT AVAILABLE: " + product.getId() + "****");

      store.addSale(newSale);
      newSale.setStore(store);
      saleRepository.save(newSale);
      System.out.println("****ORDER CONFIRMED****");

    } else {
      System.out.println("****NOT IN STOCK****\n????BACKORDER????");
      productBackOrder.put(newSale.hashCode(), newSale);
      Integer hashSale = newSale.hashCode();
      inStoreSale.setProductName("BACKORDER REFERENCE: " + hashSale.toString());
      inStoreSale.setQuantity(0);
      inStoreSale.setReceipt(hashSale.toString());
      inStoreSale.setStore(store);

    }

    EntityModel<Sale> entityModel = assembler.toModel(inStoreSale);

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
  }

  public ResponseEntity<?> updateSale(Sale newSale, Long id) {

    Sale updatedSale = saleRepository.findById(id) //
        .map(sale -> {
          sale.setProductName(newSale.getProductName());
          sale.setQuantity(newSale.getQuantity());
          return saleRepository.save(sale);
        }) //
        .orElseGet(() -> {
          newSale.setId(id);
          return saleRepository.save(newSale);
        });

    EntityModel<Sale> entityModel = assembler.toModel(updatedSale);

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
  }

}
