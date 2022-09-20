package mercury.procurems.infrastructure.brokers;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * Interface depicting all input channels
 */
public interface SaleEventSource {

    String BACKORDER_INPUT = "saleBackorderChannel";

    @Input
    SubscribableChannel saleBackorderChannel();

}
