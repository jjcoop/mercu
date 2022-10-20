package mercury.salems.application.internal.outboundservices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import mercury.shareDomain.events.Backlog;

import org.springframework.stereotype.Service;

@Service
public class BacklogEventPublisherService {

    private StreamBridge streamBridge;
    private static final Logger log = LoggerFactory.getLogger(BacklogEventPublisherService.class);

    public BacklogEventPublisherService(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void handleSaleBacklogEvent(Backlog backlogEvent) {
        log.info(backlogEvent.toString());
        streamBridge.send("output-out-0", backlogEvent);
    }
}