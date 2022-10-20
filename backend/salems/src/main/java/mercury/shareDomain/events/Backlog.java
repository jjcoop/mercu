package mercury.shareDomain.events;

public class Backlog {
    private Long saleID;
    private Double total;
    private String productName;

    public Backlog(Long saleID, Double total, String productName) {
        this.saleID = saleID;
        this.total = total;
        this.productName = productName;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "Backlog [saleID=" + saleID + ", total=" + total + ", productName=" + productName + "]";
    }

}
