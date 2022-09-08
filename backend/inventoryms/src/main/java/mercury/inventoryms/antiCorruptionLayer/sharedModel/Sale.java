package mercury.inventoryms.antiCorruptionLayer.sharedModel;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

public class Sale {
    private Long id;
    private String productName;
    private int quantity;
    private Date dateTime;
  
  
    public Sale(){}


    public Sale(Long id, String productName, int quantity) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.dateTime = new Date();
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
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


    public Date getDateTime() {
        return dateTime;
    }


    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

}
