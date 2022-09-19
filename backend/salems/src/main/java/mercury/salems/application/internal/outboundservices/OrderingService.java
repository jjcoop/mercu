package mercury.salems.application.internal.outboundservices;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import mercury.shareDomain.Order;

@Service
public class OrderingService {
    
    public Order send(Order order) {
        String endPoint = "http://localhost:8788/productInventory/orders/";

        System.out.println(order.getStatusCode());

        RestTemplate restTemplate = new RestTemplate();
        Order returnOrder = restTemplate.postForObject(endPoint, order, Order.class);
        assert returnOrder != null;
        System.out.println("****CREATE****" + returnOrder.getStatusCode());

        return order;
    }

}