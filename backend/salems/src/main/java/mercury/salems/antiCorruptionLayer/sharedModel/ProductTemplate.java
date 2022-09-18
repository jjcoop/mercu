package mercury.salems.antiCorruptionLayer.sharedModel;

import java.util.Date;
import java.util.Collections;
import java.util.Set;


public class ProductTemplate {

  private Long id;
  private String productName;
  private String description;
  private int quantity;
  private Date dateTime;
  private Set<PartTemplate> parts = Collections.emptySet();

  public ProductTemplate(
    Long id,
    String productName,
    double price,
    String description,
    int quantity,
    Set<PartTemplate> parts
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

  public Set<PartTemplate> getParts() {
    return parts;
  }
}
