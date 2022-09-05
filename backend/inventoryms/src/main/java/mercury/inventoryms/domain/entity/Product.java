package mercury.inventoryms.domain.entity;

import mercury.inventoryms.domain.aggregate.Part;
import mercury.inventoryms.interfaces.rest.PartController;

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
@Table(name = "Tbl_Contact")
@SequenceGenerator(name="con", initialValue=30423, allocationSize=100)
public class Contact {
  @Id @Column(name = "PRODUCT_ID", unique = true, nullable = false) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="con")
  private Long pID;
  @Column(name = "PRODUCT_NAME", unique = false, nullable = false, length = 100)
  private String pName;
  @Column(name = "PRODUCT_PRICE", unique = false, nullable = false, length = 100)
  private float pPrice;
  @Column(name = "PRODUCT_COMMENT", unique = false, nullable = false, length = 100)
  private String pComment;
  @ManyToOne(cascade=CascadeType.PERSIST)
  @JoinColumn(name = "PART_ID")
  @Embedded
  private Set<Part> parts =  Collections.emptySet();
  @JsonIgnore
  private Part part;

  Product() {}

  public Product(String pName, Long pID, float pPrice, String pComment) {
    this.pName = pName;
    this.pID = pID;
    this.pPrice = pPrice;
    this.pComment = pComment;
    this.parts = getParts();
  }

  //TODO look into .ManyToOne() method here for storing multiple parts
  @JsonProperty(value = "part")
  public Link getPartName(){
    return linkTo(methodOn(PartController.class).one(part.getId())).withSelfRel();
  }
  
  public Long getId() {
    return pID;
  }

  public void setId(Long pID) {
    this.pID = pID;
  }

  public String getProductName() {
    return pName;
  }

  public void setProductName(String pName) {
    this.pName = pName;
  }

  public float getProductPrice(){
    return pPrice;
  }

  public void setProductPrice(float pPrice){
    this.pPrice = pPrice;
  }

  public String getProductComment() {
    return pComment;
  }

  public void setProductComment(String pComment) {
    this.pComment = pComment;
  }

  //TODO resume here

  public Set<Part> getParts(){
    return parts;
  }

  public void setParts(Set<Part> parts) {
    this.parts = parts;
  }

  public void addPart(Part part) {
    this.parts.add(part);
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof Product))
      return false;
    Product Product = (Product) o;
    return Objects.equals(this.pID, Product.pID) 
        && Objects.equals(this.pName, Product.pName)
        && Objects.equals(this.pPrice, Product.pPrice)
        && Objects.equals(this.pComment, Product.pComment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.pID, this.pName, this.pPrice, this.pComment);
  }

  @Override
  public String toString() {
    return "Product [name=" + pName + ", pID=" + pID + ", pPrice=" + pPrice + ", pComment=" + pComment
        + position + ", parts=" + parts + "]";
  }
  
}