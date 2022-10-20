package mercury.business.model;

public class SalesIntel {
    private Long saleID;
    private Double total;

    public SalesIntel(Long saleID, Double total) {
        this.saleID = saleID;
        this.total = total;
    }

    public Long getSaleID() {
        return saleID;
    }

    public void setSaleID(Long saleID) {
        this.saleID = saleID;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "SalesIntel [saleID=" + saleID + ", total=" + total + "]";
    }
}
