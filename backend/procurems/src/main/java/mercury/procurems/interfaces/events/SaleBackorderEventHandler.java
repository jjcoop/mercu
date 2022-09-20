package mercury.procurems.interfaces.events;

import mercury.shareDomain.events.SaleBackorderEvent;
import mercury.procurems.infrastructure.brokers.SaleEventSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

/**
 * Event Handler for the Sale Backorder Event that procurement Bounded Context is interested in.
 */
@Service
@EnableBinding(SaleEventSource.class)
public class SaleBackorderEventHandler {

    @Autowired
    private SaleEventSource saleEventSource;

    @StreamListener(SaleEventSource.BACKORDER_INPUT)
    public void receiveEvent(SaleBackorderEvent saleBackorderEvent) {
        System.out.println("**** READING FROM KAFKA TOPIC salebackorder: "+
                saleBackorderEvent.getSaleBackorderEventData().getSaleId()+" ****");
    }
}