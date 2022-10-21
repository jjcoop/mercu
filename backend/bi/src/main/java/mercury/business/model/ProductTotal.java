package mercury.business.model;

public class ProductTotal {
    private String productName;
    private Double total;

    public ProductTotal() {
    }

    public ProductTotal(String productName, Double total) {
        this.productName = productName;
        this.total = total;
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

    @Override
    public String toString() {
        return "Product Total [productName=" + productName + ", total=" + total + "]";
    }

}
