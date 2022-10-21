package mercury.business.interfaces.rest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mercury.business.application.internal.queryservices.SalesInteractiveQuery;
import mercury.business.model.ProductTotal;
import mercury.business.model.SaleIntel;

@RestController
public class BusinessController {

    @Autowired
    SalesInteractiveQuery salesInteractiveQuery;


    @GetMapping("/bi-sales/gross-profit")
    public SaleIntel getGrossProfit() {
        return salesInteractiveQuery.getGrossProfit();
    }

    @GetMapping("/bi-sales/product")
    @ResponseBody
    public Double getProductRevenue(@RequestParam String productName) {
        return salesInteractiveQuery.getProductRevenue(productName);
    }

}
