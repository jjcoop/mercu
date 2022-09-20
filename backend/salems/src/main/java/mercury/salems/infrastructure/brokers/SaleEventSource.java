package mercury.salems.infrastructure.brokers;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

/**
 * Interface depicting all input channels
 */
public interface SaleEventSource {

    @Output("saleBackorderChannel")
    SubscribableChannel saleBackorder();

}
