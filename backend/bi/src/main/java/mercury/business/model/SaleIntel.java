package mercury.business.model;

import java.util.List;

public class SaleIntel {
    private Double TotalRevenue;
    private List<ProductTotal> productIntel;
    private int quantity;

    public SaleIntel() {
    }

    public SaleIntel(List<ProductTotal> productIntel, Double totalRevenue) {
        this.productIntel = productIntel;
        TotalRevenue = totalRevenue;
    }

    public List<ProductTotal> getProductIntel() {
        return productIntel;
    }

    public void setProductIntel(List<ProductTotal> productIntel) {
        this.productIntel = productIntel;
    }

    public Double getTotalRevenue() {
        return TotalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        TotalRevenue = totalRevenue;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "SaleIntel [TotalRevenue=" + TotalRevenue + ", productIntel=" + productIntel + "]";
    }

}
