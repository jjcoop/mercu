package mercury.shareDomain.events;

public class Backlog {
    private Long saleID;
    private Double total;

    public Backlog(Long saleID, Double total) {
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
        return "Backlog [saleID=" + saleID + ", total=" + total + "]";
    }

}
