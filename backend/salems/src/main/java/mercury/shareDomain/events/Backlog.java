package mercury.shareDomain.events;

public class Backlog {
    private Long saleID;
    private Double total;
    private String productName;
    private int quantity;
    boolean isBackorder;

    public Backlog() {
    }

    public Backlog(Long saleID, Double total, String productName, int quantity, boolean isBackorder) {
        this.saleID = saleID;
        this.total = total;
        this.productName = productName;
        this.quantity = quantity;
        this.isBackorder = isBackorder;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isBackorder() {
        return isBackorder;
    }

    public void setBackorder(boolean isBackorder) {
        this.isBackorder = isBackorder;
    }

    @Override
    public String toString() {
        return "Backlog [saleID=" + saleID + ", total=" + total + ", productName=" + productName + ", quantity="
                + quantity + ", isBackorder=" + isBackorder + "]";
    }
}