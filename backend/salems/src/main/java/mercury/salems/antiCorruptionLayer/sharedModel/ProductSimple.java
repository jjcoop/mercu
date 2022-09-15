package mercury.salems.antiCorruptionLayer.sharedModel;

import java.util.Collections;
import java.util.Set;

public class ProductSimple {

  private Long id;
  private String name;
  private double price;
  private String description;
  private Set<PartSimple> parts;

  public ProductSimple(
    Long id,
    String name,
    double price,
    String description,
    Set<PartSimple> parts
  ) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.description = description;
    this.parts = parts;
  }

  public ProductSimple(
    String name,
    double price,
    String description
  ) {
    this.id = new Long(0);
    this.name = name;
    this.price = price;
    this.description = description;
    this.parts = Collections.emptySet();
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  public String getDescription() {
    return description;
  }

  public Set<PartSimple> getParts() {
    return parts;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    ProductSimple other = (ProductSimple) obj;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    if (name == null) {
      if (other.name != null) return false;
    } else if (!name.equals(other.name)) return false;
    return true;
  }

  @Override
  public String toString() {
    return (
      "Product [description=" +
      description +
      ", id=" +
      id +
      ", name=" +
      name +
      ", price=" +
      price +
      "]"
    );
  }
}
