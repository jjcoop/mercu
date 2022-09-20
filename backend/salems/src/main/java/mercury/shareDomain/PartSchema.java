package mercury.shareDomain;

public class PartSchema {
    
  private Long id;
  private String partName;
  private String description;
  private int quantity;
  private String manufacturer;

    public PartSchema() {};

  public PartSchema(
    Long id,
    String partName,
    String description,
    int quantity,
    String manufacturer
  ) {
    this.id = id;
    this.partName = partName;
    this.description = description;
    this.quantity = quantity;
    this.manufacturer = manufacturer;
  }

  public Long getId() {
    return id;
  }

  public String getPartName() {
    return partName;
  }

  public String getDescription() {
    return description;
  }

  public int getQuantity() {
    return quantity;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public String toString() {
    String retString = "part[ id=" + getId();
    retString += "part[ partName=" + getPartName();
    retString += "part[ description=" + getDescription();
    retString += "part[ quantity=" + getQuantity();
    retString += "part[ manufacturer=" + getManufacturer();
    return retString;
  }
}
