package mercury.business.application.internal.queryservices;

import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
// import java.util.NoSuchElementException;

@Service
public class SalesInteractiveQuery {

    private final InteractiveQueryService interactiveQueryService;

    //@Autowired
    public SalesInteractiveQuery(InteractiveQueryService interactiveQueryService) {
        this.interactiveQueryService = interactiveQueryService;
    }

    public List<Double> getGrossProfit() {
        List<Double> saleList = new ArrayList<>();
        KeyValueIterator<Long, Double> salesIntel = SalesIntelStore().all();
        KeyValueIterator<Long, Double> backlogs = BacklogStore().all();
        
        while (salesIntel.hasNext()) {
            Double next = salesIntel.next().value;
            saleList.add(next);
        }

        while (backlogs.hasNext()) {
            Double next = backlogs.next().value;
            saleList.add(next);
        }

        return saleList;
    }

    private ReadOnlyKeyValueStore<Long, Double> BacklogStore() {
        return this.interactiveQueryService.getQueryableStore(SalesStreamProcessing.BACKLOG_STATE_STORE,
                QueryableStoreTypes.keyValueStore());
    }

    private ReadOnlyKeyValueStore<Long, Double> SalesIntelStore() {
        return this.interactiveQueryService.getQueryableStore(SalesStreamProcessing.SALESINTEL_STATE_STORE,
                QueryableStoreTypes.keyValueStore());
    }

    // public long getBrandQuantity(String brandName) {
    //     if (brandStore().get(brandName) != null) {
    //         return brandStore().get(brandName);
    //     } else {
    //         throw new NoSuchElementException(); //TODO: should use a customised exception.
    //     }
    // }



    // public List<String> getEquipmentListByBrand(String brandString) {
    //     List<String> equipmentList = new ArrayList<>();
    //     KeyValueIterator<String, Equipment> all = equipmentStore().all();
    //     while (all.hasNext()) {
    //         Equipment equipment = all.next().value;
    //         String brand_name = equipment.getBrand();
    //         String equipment_name = equipment.getEquipment();
    //         if (brand_name.equals(brandString)){
    //             equipmentList.add(equipment_name);
    //         }
    //     }
    //     return equipmentList;
    // }

    // public long getSaleQuantity(String iD) {
    //     return 0;
    // }



    // public List<String> getProductBySale(String iD) {
    //     return null;
    // }

    // public List<String> getProductsList() {
    //     return null;
    // }


}
