package mercury.inventoryms.domain.aggregate;
import mercury.inventoryms.domain.entity.Part;

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

  @Column(name = "NAME", unique = false, nullable = false, length = 100)
  private String name;
  @Column(name = "PRICE", unique = false, nullable = false, length = 100)
  private double price;
  @Column(name = "DESCRIPTION", unique = false, nullable = false, length = 100)
  private String description;
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "PRODUCT")
  @Embedded
  private Set<Part> parts =  Collections.emptySet();

  public Product(){}

  public Product(String name, double price, String description) {

    this.name = name;
    this.price = price;
    this.description = description;
    this.parts = getParts();

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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
    return "Product [name=" + name + ", price=" + price + ", description=" + description + ", parts=" + parts + ", id=" + id + "]";
  }
  
}