package mercury.inventoryms.domain.aggregate;
import mercury.inventoryms.domain.entity.Part;
import mercury.inventoryms.domain.valueObject.ProductDescription;
import mercury.inventoryms.domain.valueObject.ProductPrice;
import mercury.inventoryms.domain.valueObject.ProductName;

import java.util.Collections;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Tbl_Product")
@SequenceGenerator(name="prod", initialValue=777677, allocationSize=100)
public class Product {
  
  @Column(name = "ID", unique = true, nullable = false)
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="prod")
  @Id Long id;

  @Embedded
  private ProductName name;
  @Embedded
  private ProductPrice price;
  @Embedded
  private ProductDescription description;
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "PRODUCT")
  @Embedded
  private Set<Part> parts =  Collections.emptySet();

  public Product(){}

  public Product(String name, double price, String description) {

    this.name = new ProductName(name);
    this.price = new ProductPrice(price);
    this.description = new ProductDescription(description);
    this.parts = getParts();

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name.getValue();
  }

  public void setName(String name) {
    this.name = new ProductName(name);
  }

  public double getPrice() {
    return price.getValue();
  }

  public void setPrice(double price) {
    this.price = new ProductPrice(price);
  }

  public String getDescription() {
    return description.getValue();
  }

  public void setDescription(String description) {
    this.description = new ProductDescription(description);
  }

  public Set<Part> getParts() {
    return parts;
  }

  public void setParts(Set<Part> parts) {
    this.parts = parts;
  }

  public void addPart(Part part){
    this.parts.add(part);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Product))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Product [name=" + getName() + ", price=" + getPrice() + ", description=" + getDescription() + ", parts=" + parts + ", id=" + id + "]";
  }
  
}