package mercury.shareDomain.events;

public class SaleBackorderEventData {

    private Long saleId;

    public SaleBackorderEventData() {
    }

    public SaleBackorderEventData(Long saleId) {
        this.saleId = saleId;

    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public Long getSaleId() {
        return this.saleId;
    }

}
