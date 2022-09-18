package mercury.salems.application.internal.commandservices;

import mercury.salems.interfaces.rest.transform.SaleModelAssembler;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mercury.salems.domain.aggregate.Sale;
import mercury.salems.antiCorruptionLayer.sharedModel.ProductTemplate;
import mercury.salems.antiCorruptionLayer.sharedModel.PartTemplate;
import mercury.salems.application.internal.queryservices.SaleNotFoundException;
import mercury.salems.application.internal.queryservices.StoreNotFoundException;
import mercury.salems.domain.aggregate.InStoreSale;
import mercury.salems.domain.aggregate.OnlineSale;
import mercury.salems.domain.entity.Store;
import mercury.salems.infrastructure.repository.SaleRepository;
import mercury.salems.infrastructure.repository.StoreRepository;

@Service
public class SaleCommandService {
  @Autowired
  private SaleRepository<OnlineSale> onlineSaleRepository;
  @Autowired
  private SaleRepository<InStoreSale> inStoreSaleRepository;
  private StoreRepository storeRepository;
  private SaleModelAssembler assembler;
  private RestTemplate restTemplate;
  private Map<Integer, Sale> productBackOrder;

  public SaleCommandService(SaleRepository<OnlineSale> onlineSaleRepository,
      SaleRepository<InStoreSale> inStoreSaleRepository, StoreRepository storeRepository,
      SaleModelAssembler assembler, RestTemplate restTemplate, Map<Integer, Sale> productBackOrder) {
    this.onlineSaleRepository = onlineSaleRepository;
    this.inStoreSaleRepository = inStoreSaleRepository;
    this.storeRepository = storeRepository;
    this.assembler = assembler;
    this.restTemplate = restTemplate;
    this.productBackOrder = productBackOrder;
  }

  // Sale
  public ResponseEntity<?> addSale(OnlineSale newSale) {
    Date date = new Date();
    newSale.setDateTime(date);
    EntityModel<OnlineSale> entityModel = assembler.toModel(onlineSaleRepository.save(newSale));

    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  public Sale replaceSale(Sale newSale, long id) {
    Sale updatedSale = onlineSaleRepository.findById(id) //
        .map(sale -> {
          sale.setProductName(newSale.getProductName());
          sale.setDateTime(newSale.getDateTime());
          return onlineSaleRepository.save(sale);
        }) //
        .orElseGet(() -> {
          newSale.setId(id);
          return onlineSaleRepository.save(newSale);
        });

    return updatedSale;
  }

  public ResponseEntity<?> updateSale(Sale newSale, Long id) {

    Sale updatedSale = onlineSaleRepository.findById(id) //
        .map(sale -> {
          sale.setProductName(newSale.getProductName());
          sale.setQuantity(newSale.getQuantity());
          return onlineSaleRepository.save(sale);
        }) //
        .orElseGet(() -> {
          newSale.setId(id);
          return onlineSaleRepository.save(newSale);
        });

    EntityModel<Sale> entityModel = assembler.toModel(updatedSale);

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
  }


  // OnlineSale
  public ResponseEntity<?> addOnlineSale(OnlineSale newSale) {
    Date date = new Date();
    newSale.setDateTime(date);
    EntityModel<OnlineSale> entityModel = assembler.toModel(onlineSaleRepository.save(newSale));

    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }


  // InStoreSale
  public ResponseEntity<?> addInStoreSale(Long id, InStoreSale newSale) {
    // set date time of sale
    Date date = new Date();
    newSale.setDateTime(date);

    // set store to the store corresponding with the id
    Store store = storeRepository.findById(id).orElseThrow(() -> new SaleNotFoundException(id));
    newSale.setStore(store);

    String productURL = "http://localhost:8788/productInventory/" + newSale.getProductId();
    //ProductTemplate product = new ProductTemplate(new Long(0), newSale.getProductName(), 1.0, "TMP", 1);
    ProductTemplate product = restTemplate.getForObject(productURL, ProductTemplate.class);

    System.out.println("Have " + product.getQuantity() + ", need " + newSale.getQuantity());


    // set up return entity, default to null
    EntityModel<InStoreSale> entityModel = null;

    // if product quantity < required quantity check part quantity >= required quantity
    if(product.getQuantity() < newSale.getQuantity()) {
      Set<PartTemplate> parts = product.getParts();
      boolean haveEnoughParts = true;
      for (PartTemplate part : parts) {
        String partURL = "http://localhost:8788/partInventory/" + part.getId();
        PartTemplate foundPart = restTemplate.getForObject(partURL, PartTemplate.class);
        if(foundPart.getQuantity() < newSale.getQuantity()) {
          // TODO temporary alert, remove after managing backorder
          System.out.println("PartId = " + foundPart.getId() + " PartName = " + foundPart.getPartName() + " has a lower quanitity than requested. Have " + foundPart.getQuantity() + ", need " + newSale.getQuantity());
          haveEnoughParts = false;
        }
      }
      if (haveEnoughParts) {
        entityModel = assembler.toModel(inStoreSaleRepository.save(newSale));
      } else {
        // TODO offer backorder
      }

    } else {  // else there are enough products to fulfill the order
      System.out.println("Quantity is ok. Have " + product.getQuantity() + ", need " + newSale.getQuantity());
      entityModel = assembler.toModel(inStoreSaleRepository.save(newSale));
    }

    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }


  public ResponseEntity<?> addStoreSale(Long id, InStoreSale newSale) {
    InStoreSale inStoreSale = newSale;
    Store store = storeRepository.findById(id)
        .orElseThrow(() -> new SaleNotFoundException(id));

    String rootUrl = "http://localhost:8788/productInventory/" + newSale.getProductId();
    //ProductTemplate product = new ProductTemplate(new Long(0), newSale.getProductName(), 1.0, "TMP", 1);
    ProductTemplate product = restTemplate.getForObject(rootUrl, ProductTemplate.class);
    //product = restTemplate.postForObject(rootUrl, product, ProductTemplate.class);

    if (product != null) {
      System.out.println("****PRODUCT AVAILABLE: " + product.getId() + "****");

      store.addSale(newSale);
      newSale.setStore(store);
      onlineSaleRepository.save(newSale);
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
    // EntityModel<Sale> entityModel = assembler.toModel(inStoreSale);
    EntityModel<InStoreSale> entityModel = assembler.toModel(inStoreSale);

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
  }

  
    // Store
    public ResponseEntity<?> addStore(Store newStore) {

      EntityModel<Store> entityModel = assembler.toModel(storeRepository.save(newStore));
  
      return ResponseEntity
          .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
          .body(entityModel);
    }

}
