package mercury.shareDomain;

import java.net.URI;
import java.util.Date;

public class Order {
    private Long saleID;
    private String statusCode;
    private String productName;
    private URI productURI = URI.create("");
    private Integer quantity;
    private Double total;
    private Date dateTime = new Date();

    public Order() {
    }

    public Order(Long saleID, String statusCode, String productName, Integer quantity) {
        this.saleID = saleID;
        this.statusCode = statusCode;
        this.productName = productName;
        this.quantity = quantity;
    }

    public Order(Long saleID, String statusCode, String productName, URI productURI, Integer quantity, Double total,
            Date dateTime) {
        this.saleID = saleID;
        this.statusCode = statusCode;
        this.productName = productName;
        this.productURI = productURI;
        this.quantity = quantity;
        this.total = total;
        this.dateTime = dateTime;
    }

    public Long getSaleID() {
        return saleID;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getProductName() {
        return productName;
    }

    public URI getProductURI() {
        return productURI;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getTotal() {
        return total;
    }

    public Date getDateTime() {
        return dateTime;
    }
}