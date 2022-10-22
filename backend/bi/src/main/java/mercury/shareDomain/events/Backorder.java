package mercury.shareDomain.events;

import java.util.Date;

public class Backorder {

    private Long saleID;
    private String productName;
    private Double total;
    private Date dateTime = new Date();
    
    public Backorder(Long saleID, Double total) {
        this.saleID = saleID;
        this.total = total;
    }

    public Long getSaleID() {
        return saleID;
    }

    public void setSaleID(Long saleID) {
        this.saleID = saleID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Date getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "Backorder [saleID=" + saleID + ", productName=" + productName + ", total=" + total + ", dateTime="
                + dateTime + "]";
    }

}