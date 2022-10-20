package mercury.salems.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;

import mercury.salems.domain.aggregate.InStoreSale;
import mercury.salems.domain.aggregate.OnlineSale;
import mercury.salems.domain.valueObject.SaleProductName;
import mercury.shareDomain.Order;

@Service
public class ExternalOrderSaleService {

    public OnlineSale processOrder(Order order, OnlineSale oldSale) {
        OnlineSale sale = toOnlineSale(order, oldSale.getCustomerName(), oldSale.getAddress());
        return sale;
    }

    // ACL for onlineSale
    public OnlineSale toOnlineSale(Order order, String cName, String cAddress) {
        OnlineSale sale = new OnlineSale(order.getSaleID(), order.getProductName(), order.getQuantity(), cName,
                cAddress);
        sale.setOrderStatus(order.getStatusCode());
        sale.setTotal(order.getTotal());
        SaleProductName productName = new SaleProductName(order.getProductName(), order.getProductURI());
        sale.setProductName(productName);

        return sale;
    }

    public InStoreSale processOrder(Order order, InStoreSale oldSale) {
        InStoreSale sale = toInStoreSale(order, oldSale.getReceipt());
        return sale;
    }

    // ACL for inStoreSale
    public InStoreSale toInStoreSale(Order order, String reciept) {
        InStoreSale sale = new InStoreSale(order.getSaleID(), order.getProductName(), order.getQuantity(), reciept);
        sale.setOrderStatus(order.getStatusCode());
        sale.setTotal(order.getTotal());
        SaleProductName productName = new SaleProductName(order.getProductName(), order.getProductURI());
        sale.setProductName(productName);

        return sale;
    }
}
