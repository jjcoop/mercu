package mercury.business.application.internal.queryservices;

import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;

@Service
public class SalesInteractiveQuery {

    private final InteractiveQueryService interactiveQueryService;

    //@Autowired
    public SalesInteractiveQuery(InteractiveQueryService interactiveQueryService) {
        this.interactiveQueryService = interactiveQueryService;
    }

    public Double getGrossProfit() {
        Double grossRevenue = 0.0;
        KeyValueIterator<Long, Double> backlogs = BacklogStore().all();
        while (backlogs.hasNext()) {
            Double next = backlogs.next().value;
            grossRevenue += next;
        }
        return grossRevenue;
    }

    private ReadOnlyKeyValueStore<Long, Double> BacklogStore() {
        return this.interactiveQueryService.getQueryableStore(SalesStreamProcessing.BACKLOG_STATE_STORE,
                QueryableStoreTypes.keyValueStore());
    }
}
