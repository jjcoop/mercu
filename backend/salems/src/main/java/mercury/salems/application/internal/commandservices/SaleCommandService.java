package mercury.salems.application.internal.commandservices;

import mercury.salems.interfaces.rest.transform.SaleModelAssembler;
import mercury.salems.model.Product;

import java.util.Date;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mercury.salems.domain.aggregate.Sale;
import mercury.salems.domain.aggregate.InStoreSale;
import mercury.salems.domain.entity.Store;
import mercury.salems.infrastructure.repository.SaleRepository;
import mercury.salems.infrastructure.repository.StoreRepository;
import mercury.salems.application.internal.queryservices.SaleNotFoundException;


@Service
public class SaleCommandService {
    private SaleRepository saleRepository;
    private StoreRepository storeRepository;
    private SaleModelAssembler assembler;
    private RestTemplate restTemplate;
	  final String rootUrl = "http://localhost:8788/productInventory/sale";


    public SaleCommandService(SaleRepository saleRepository, StoreRepository storeRepository, SaleModelAssembler assembler, RestTemplate restTemplate) {
        this.saleRepository = saleRepository;
        this.storeRepository = storeRepository;
        this.assembler = assembler;
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<?> addSale(Sale newSale) {
  
      Date date = new Date();
      newSale.setDateTime(date);
      EntityModel<Sale> entityModel = assembler.toModel(saleRepository.save(newSale));
    
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

    public Sale replaceSale(Sale newSale, long id){
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
      
			Product product = new Product();
			product.setName(newSale.getProductName());
      product.setDescription("description");
      product.setPrice(12.34);
			product = restTemplate.postForObject(rootUrl, product, Product.class);
			// product = restTemplate.getForObject(rootUrl+"/"+product.getId(), Product.class);
			assert product != null;
			System.out.println("****CREATE****" + product);

      Store store = storeRepository.findById(id)
          .orElseThrow(() -> new SaleNotFoundException(id));
  
      store.addSale(newSale);
      newSale.setStore(store);
      saleRepository.save(newSale);
  
      EntityModel<Sale> entityModel = assembler.toModel(newSale);
  
      return ResponseEntity //
          .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
          .body(entityModel);
    }      
    
    public ResponseEntity<?> updateSale( Sale newSale, Long id) {

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
