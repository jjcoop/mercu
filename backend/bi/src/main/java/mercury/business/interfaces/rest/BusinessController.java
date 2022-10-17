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

    @GetMapping("/brand/{brandName}/quantity")
    long getBrandQuantityByName(@PathVariable String brandName) {
        return salesInteractiveQuery.getBrandQuantity(brandName);
    }

    @GetMapping("/brands/all")
    List<String> getAllBrands() {
        return salesInteractiveQuery.getBrandList();
    }


    @GetMapping("/brand/{brandName}/equipments")
    List<String> getAllEquipmentsByBrand(@PathVariable String brandName) {
        return  salesInteractiveQuery.getEquipmentListByBrand(brandName);
    }

    @GetMapping("/brand/all-equipments")
    List<String> getAllEquipments() {
        return  salesInteractiveQuery.getEquipmentList();
    }

}
