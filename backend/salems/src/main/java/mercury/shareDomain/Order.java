package mercury.shareDomain;

import java.util.Date;

public class Order {
    private Long saleID;
    private Integer statusCode;
    private String productName;
    private Integer quantity;
    private Date dateTime = new Date();

    public Order(Long saleID, Integer statusCode, String productName, Integer quantity) {
        this.saleID = saleID;
        this.statusCode = statusCode;
        this.productName = productName;
        this.quantity = quantity;
    }

    public Long getSaleID() {
        return saleID;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Date getDateTime() {
        return dateTime;
    }

}