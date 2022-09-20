package mercury.shareDomain.events;

public class SaleBackorderEvent {

    SaleBackorderEventData saleBackorderEventData;

    public SaleBackorderEvent() {
    }

    public SaleBackorderEvent(SaleBackorderEventData saleBackorderEventData) {
        this.saleBackorderEventData = saleBackorderEventData;
    }

    public void setSaleBackorderEventData(SaleBackorderEventData saleBackorderEventData) {
        this.saleBackorderEventData = saleBackorderEventData;
    }

    public SaleBackorderEventData getSaleBackorderEventData() {
        return saleBackorderEventData;
    }
}