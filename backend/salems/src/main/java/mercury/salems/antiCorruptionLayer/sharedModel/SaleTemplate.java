package mercury.salems.antiCorruptionLayer.sharedModel;

import java.util.Date;

public class SaleTemplate {

  private Long id;
  private String productName;
  private int quantity;
  private Date dateTime;

  public SaleTemplate(Long id, String productName, int quantity, Date dateTime) {
    this.id = id;
    this.productName = productName;
    this.quantity = quantity;
    this.dateTime = dateTime;
  }

  public Long getId() {
    return id;
  }

  public String getProductName() {
    return productName;
  }

  public int getQuantity() {
    return quantity;
  }

  public Date getDateTime() {
    return dateTime;
  }
}
