package mercury.inventoryms.application.internal.outboundservices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import mercury.shareDomain.events.Backlog;

import org.springframework.stereotype.Service;

@Service
public class BackorderEventPublisherService {

    private StreamBridge streamBridge;
    private static final Logger log = LoggerFactory.getLogger(BackorderEventPublisherService.class);

    public BackorderEventPublisherService(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void handleSaleBackorderEvent(Backlog backorderEvent) {
        log.info(backorderEvent.toString());
        streamBridge.send("output-out-0", backorderEvent);
    }
}