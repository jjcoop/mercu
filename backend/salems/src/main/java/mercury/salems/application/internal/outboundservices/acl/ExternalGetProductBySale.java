package mercury.salems.application.internal.outboundservices.acl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mercury.salems.application.internal.queryservices.SaleNotFoundException;
import mercury.salems.domain.aggregate.InStoreSale;
import mercury.salems.domain.aggregate.OnlineSale;
import mercury.salems.domain.aggregate.Sale;
import mercury.salems.infrastructure.repository.SaleRepository;
import mercury.shareDomain.ProductSchema;

@Service
public class ExternalGetProductBySale {
    @Autowired
    SaleRepository<OnlineSale> onlineSaleRepository;
    @Autowired
    SaleRepository<InStoreSale> inStoreSaleRepository;

    public ProductSchema getProductBySale(Long saleId) {
        RestTemplate restTemplate = new RestTemplate();
        
        Sale sale = onlineSaleRepository.findById(saleId).orElse(inStoreSaleRepository.findById(saleId).orElseThrow(() -> new SaleNotFoundException(saleId)));
        String uri = sale.getproductURI() + "/schema";
        return restTemplate.getForObject(uri, ProductSchema.class);
    }
}
