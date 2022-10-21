package mercury.business.application.internal.queryservices;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;

import mercury.business.model.ProductTotal;
import mercury.business.model.SaleIntel;

@Service
public class SalesInteractiveQuery {

    private final InteractiveQueryService interactiveQueryService;
    private DecimalFormat df = new DecimalFormat("0.00");

    //@Autowired
    public SalesInteractiveQuery(InteractiveQueryService interactiveQueryService) {
        this.interactiveQueryService = interactiveQueryService;
    }

    public SaleIntel getGrossProfit() {
        SaleIntel saleIntel = new SaleIntel();
        Double salesTotal = 0.0;
        List<ProductTotal> revenue = new ArrayList<>();
        KeyValueIterator<String, Double> backlogs = BacklogStore().all();
        while (backlogs.hasNext()) {
            String name = backlogs.peekNextKey();
            Double value = backlogs.next().value;
            salesTotal += Double.valueOf(df.format(value));
            revenue.add(new ProductTotal(name, Double.valueOf(df.format(value))));
        }
        saleIntel.setProductIntel(revenue);
        saleIntel.setTotalRevenue(salesTotal);
        return saleIntel;
    }

    public Double getProductRevenue(String productName) {
        Double productRevenue = 0.0;
        KeyValueIterator<String, Double> backlogs = BacklogStore().all();
        while (backlogs.hasNext()) {
            String product = backlogs.peekNextKey();
            Double value = backlogs.next().value;
            if (product.equals(productName)) {
                productRevenue = Double.valueOf(df.format(value)); 
            }
        }
        return productRevenue;
    }

    private ReadOnlyKeyValueStore<String, Double> BacklogStore() {
        return this.interactiveQueryService.getQueryableStore(SalesStreamProcessing.BACKLOG_STATE_STORE,
                QueryableStoreTypes.keyValueStore());
    }


}
