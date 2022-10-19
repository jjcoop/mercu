package mercury.business.interfaces.rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import mercury.business.application.internal.queryservices.SalesInteractiveQuery;

import java.util.List;

@RestController
public class BusinessController {

    @Autowired
    SalesInteractiveQuery salesInteractiveQuery;

    @GetMapping("/bi-sales/gross-profit")
    List<String> getGrossProfit() {
        return salesInteractiveQuery.getGrossProfit();
    }

    @GetMapping("/bi-sale/{ID}/quantity")
    long getSaleByID(@PathVariable String ID) {
        return salesInteractiveQuery.getSaleQuantity(ID);
    }

    @GetMapping("/bi-sales/all")
    List<String> getAllSales() {
        return salesInteractiveQuery.getSaleList();
    }


    @GetMapping("/bi-sales/{ID}/products")
    List<String> getProductBysale(@PathVariable String ID) {
        return  salesInteractiveQuery.getProductBySale(ID);
    }

    @GetMapping("/bi-sale/all-products")
    List<String> getAllProducts() {
        return  salesInteractiveQuery.getProductsList();
    }

}
