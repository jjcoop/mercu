package mercury.business.application.internal.queryservices;

import mercury.shareDomain.Order;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SalesInteractiveQuery {

    private final InteractiveQueryService interactiveQueryService;

    //@Autowired
    public SalesInteractiveQuery(InteractiveQueryService interactiveQueryService) {
        this.interactiveQueryService = interactiveQueryService;
    }

    public long getBrandQuantity(String brandName) {
        if (brandStore().get(brandName) != null) {
            return brandStore().get(brandName);
        } else {
            throw new NoSuchElementException(); //TODO: should use a customised exception.
        }
    }

    public List<String> getBrandList() {
        List<String> brandList = new ArrayList<>();
        KeyValueIterator<String, Long> all = brandStore().all();
        while (all.hasNext()) {
            String next = all.next().key;
            brandList.add(next);
        }
        return brandList;
    }

    public List<String> getEquipmentListByBrand(String brandString) {
        List<String> equipmentList = new ArrayList<>();
        KeyValueIterator<String, Equipment> all = equipmentStore().all();
        while (all.hasNext()) {
            Equipment equipment = all.next().value;
            String brand_name = equipment.getBrand();
            String equipment_name = equipment.getEquipment();
            if (brand_name.equals(brandString)){
                equipmentList.add(equipment_name);
            }
        }
        return equipmentList;
    }


    public List<String> getEquipmentList() {
        List<String> equipmentList = new ArrayList<>();
        KeyValueIterator<String, Equipment> all = equipmentStore().all();
        while (all.hasNext()) {
            String equipment_name = all.next().value.getEquipment();
            equipmentList.add(equipment_name);
        }

        return equipmentList;
    }

    private ReadOnlyKeyValueStore<String, Long> brandStore() {
        return this.interactiveQueryService.getQueryableStore(ApplianceStreamProcessing.BRAND_STATE_STORE,
                QueryableStoreTypes.keyValueStore());
    }


    private ReadOnlyKeyValueStore<String, Equipment> equipmentStore() {
        return this.interactiveQueryService.getQueryableStore(ApplianceStreamProcessing.EQUIPMENT_STATE_STORE,
                QueryableStoreTypes.keyValueStore());
    }

    public long getSaleQuantity(String iD) {
        return 0;
    }

    public List<String> getSaleList() {
        return null;
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
