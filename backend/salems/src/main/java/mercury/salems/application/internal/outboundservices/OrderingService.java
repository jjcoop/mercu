package mercury.salems.application.internal.outboundservices;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mercury.shareDomain.Order;

@Service
public class OrderingService {
    
    public Order send(Order order) {
        RestTemplate restTemplate = new RestTemplate();
        String endPoint = "http://localhost:8788/productInventory/orders/";
        Order returnOrder = restTemplate.postForObject(endPoint, order, Order.class);

        return returnOrder;
    }

}