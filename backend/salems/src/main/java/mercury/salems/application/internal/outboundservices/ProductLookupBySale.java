package mercury.salems.application.internal.outboundservices;

import mercury.salems.application.internal.queryservices.SaleNotFoundException;
import mercury.salems.domain.aggregate.InStoreSale;
import mercury.salems.domain.aggregate.OnlineSale;
import mercury.salems.domain.aggregate.Sale;
import mercury.salems.infrastructure.repository.SaleRepository;
import mercury.shareDomain.ProductSchema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductLookupBySale {
    @Autowired
  private SaleRepository<OnlineSale> onlineSaleRepository;
  @Autowired
  private SaleRepository<InStoreSale> inStoreSaleRepository;

    public ResponseEntity<?> getProductBySale(Long saleId) {
        RestTemplate restTemplate = new RestTemplate();
        
        Sale sale = onlineSaleRepository.findById(saleId).orElse(inStoreSaleRepository.findById(saleId).orElseThrow(() -> new SaleNotFoundException(saleId)));
        
        return restTemplate.getForEntity(sale.getproductURI(), ProductSchema.class);
    }

}