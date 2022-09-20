package mercury.shareDomain;

import java.net.URI;

public class PartSchema {
    
  private Long id;
  private String partName;
  private String description;
  private int quantity;
  private String manufacturer;
  private URI manufacturerURI;

    public PartSchema() {};

  public PartSchema(
    Long id,
    String partName,
    String description,
    int quantity,
    String manufacturer,
    URI manufacturerURI
  ) {
    this.id = id;
    this.partName = partName;
    this.description = description;
    this.quantity = quantity;
    this.manufacturer = manufacturer;
    this.manufacturerURI = manufacturerURI;
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

  public URI getManufacturerURI() {
    return manufacturerURI;
  }

  public String toString() {
    String retString = "part[ id=" + getId();
    retString += " partName=" + getPartName();
    retString += " description=" + getDescription();
    retString += " quantity=" + getQuantity();
    retString += " manufacturer=" + getManufacturer();
    retString += " manufacturerURI=" + getManufacturerURI();
    return retString;
  }
}
