package mercury.inventoryms.domain.entity;

import mercury.inventoryms.domain.aggregate.Product;
import mercury.inventoryms.interfaces.rest.ProductController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.Link;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "Tbl_Part")
@SequenceGenerator(name="par", initialValue=30423, allocationSize=100)
public class Part {
  @Id @Column(name = "ID", unique = true, nullable = false) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="par")
  private Long id;
  @Column(name = "PART_NAME", unique = false, nullable = false, length = 100)
  private String partName;
  @Column(name = "PART_DESCRIPTION", unique = false, nullable = false, length = 100)
  private String partDescription;
  @ManyToOne(cascade=CascadeType.PERSIST)
  @JoinColumn(name = "PRODUCT_ID")
  @JsonIgnore
  private Product product;

  Part() {}

  public Part(Long id, String partName, String partDescription) {
    this.id = id;
    this.partName = partName;
    this.partDescription = partDescription;
  }

  @JsonProperty(value = "product")
  public Link getProductName(){
    return linkTo(methodOn(ProductController.class).one(product.getId())).withSelfRel();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPartName() {
    return partName;
  }

  public void setPartName(String partName) {
    this.partName = partName;
  }

  public String getPartDescription() {
    return partDescription;
  }

  public void setPartDescription(String partDescription) {
    this.partDescription = partDescription;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof Part))
      return false;
    Part Part = (Part) o;
    return Objects.equals(this.id, Part.id)
        && Objects.equals(this.partName, Part.partName)
        && Objects.equals(this.partDescription, Part.partDescription);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.partName, this.partDescription);
  }

  @Override
  public String toString() {
    return "Part [name=" + partName + ", description=" + partDescription + ", id=" + id + ", product=" + product  + "]";
  }
  
  
}