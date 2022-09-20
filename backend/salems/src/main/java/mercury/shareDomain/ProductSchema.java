package mercury.shareDomain;

import java.util.Date;
import java.io.Serializable;
import java.util.Collections;
import java.util.Set;


public class ProductSchema implements Serializable {

  private Long id;
  private String productName;
  private String description;
  private int quantity;
  private Date dateTime;
  private Set<PartSchema> parts = Collections.emptySet();

  public ProductSchema() {};

  public ProductSchema(
    Long id,
    String productName,
    double price,
    String description,
    int quantity,
    Set<PartSchema> parts
  ) {
    this.id = id;
    this.productName = productName;
    this.description = description;
    this.quantity = quantity;
    this.parts = parts;
  }

  public Long getId() {
    return id;
  }

  public String getProductName() {
    return productName;
  }

  public String getDescription() {
    return description;
  }

  public int getQuantity() {
    return quantity;
  }

  public Date getDateTime() {
    return dateTime;
  }

  public Set<PartSchema> getParts() {
    return parts;
  }

  public String toString() {
    String retString = "product[ id=" + getId();
    retString += " productName = " + getProductName();
    retString += " description = " + getDescription();
    retString += " quantity = " + getQuantity();
    retString += " parts[ ";
    for (PartSchema partTemp : getParts()) {
      retString += partTemp.toString();
    }
    retString += "]";
    return retString;
  }
}