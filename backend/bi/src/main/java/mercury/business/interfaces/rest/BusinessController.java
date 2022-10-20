package mercury.business.interfaces.rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import mercury.business.application.internal.queryservices.SalesInteractiveQuery;

@RestController
public class BusinessController {

    @Autowired
    SalesInteractiveQuery salesInteractiveQuery;


    @GetMapping("/bi-sales/gross-profit")
    Double getGrossProfit() {
        return salesInteractiveQuery.getGrossProfit();
    }
}
