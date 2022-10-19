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

    public List<String> getSaleList() {
        List<String> saleList = new ArrayList<>();
        KeyValueIterator<String, Long> backorders = BackorderStore().all();
        KeyValueIterator<String, Long> backlogs = BacklogStore().all();
        
        while (backorders.hasNext()) {
            String next = backorders.next().key;
            saleList.add(next);
        }

        while (backlogs.hasNext()) {
            String next = backlogs.next().key;
            saleList.add(next);
        }

        return saleList;
    }

    private ReadOnlyKeyValueStore<String, Long> BacklogStore() {
        return this.interactiveQueryService.getQueryableStore(SalesStreamProcessing.BACKLOG_STATE_STORE,
                QueryableStoreTypes.keyValueStore());
    }

    private ReadOnlyKeyValueStore<String, Long> BackorderStore() {
        return this.interactiveQueryService.getQueryableStore(SalesStreamProcessing.BACKORDER_STATE_STORE,
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

    public long getSaleQuantity(String iD) {
        return 0;
    }



    public List<String> getProductBySale(String iD) {
        return null;
    }

    public List<String> getProductsList() {
        return null;
    }

    public List<String> getGrossProfit() {
        return null;
    }


}
