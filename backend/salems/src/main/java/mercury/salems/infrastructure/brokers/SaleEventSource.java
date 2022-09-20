package mercury.salems.infrastructure.brokers;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * Interface depicting all input channels
 */
public interface SaleEventSource {

    @Input
    SubscribableChannel salebackorder();

}
