package mercury.salems.application.internal.outboundservices;

import mercury.salems.infrastructure.brokers.SaleEventSource;
import mercury.shareDomain.events.SaleBackorderEvent;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 *
 */
@Service
@EnableBinding(SaleEventSource.class)
public class BackorderEventPublisherService {

    SaleEventSource saleEventSource;

    public BackorderEventPublisherService(SaleEventSource saleEventSource){
        this.saleEventSource = saleEventSource;
    }

    @TransactionalEventListener
    public void handleCargoBookedEvent(SaleBackorderEvent saleBackorderEvent){
        saleEventSource.saleBackorder().send(MessageBuilder.withPayload(saleBackorderEvent).build());
    }
}