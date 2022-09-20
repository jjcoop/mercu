package mercury.inventoryms.domain.aggregate;

import mercury.inventoryms.interfaces.rest.ProductController;
import mercury.inventoryms.domain.valueObject.PartName;
import mercury.inventoryms.domain.valueObject.Quantity;
import mercury.inventoryms.domain.valueObject.Manufacturer;
import mercury.inventoryms.domain.valueObject.PartDescription;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
@SequenceGenerator(name = "par", initialValue = 30423, allocationSize = 100)
public class Part {
  @Id
  @Column(name = "ID", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "par")
  private Long id;
  @Embedded
  private PartName partName;
  @Embedded
  private PartDescription partDescription;
  @Embedded
  private Manufacturer manufacturer;
  @Embedded
  private Quantity quantity;
  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "PRODUCT_ID")
  @JsonIgnore
  private Product product;

  public Part() {
  }

  public Part(Long id, String partName, String partDescription, String supplierName, Integer quantity) {
    this.id = id;
    this.partName = new PartName(partName);
    this.partDescription = new PartDescription(partDescription);
    this.manufacturer = new Manufacturer(supplierName);
    this.quantity = new Quantity(quantity);
  }

  @JsonProperty(value = "productURI")
  public URI getProductName() {
    return linkTo(methodOn(ProductController.class).getProduct(product.getId())).toUri();
  }

  public void updatePart(Part newPart) {
    this.partName = newPart.partName;
    this.partDescription = newPart.partDescription;
    this.manufacturer = newPart.manufacturer;
    this.quantity = newPart.quantity;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPartName() {
    return partName.getValue();
  }

  public void setPartName(String partName) {
    this.partName = new PartName(partName);
  }

  public String getPartDescription() {
    return partDescription.getValue();
  }

  public void setPartDescription(String partDescription) {
    this.partDescription = new PartDescription(partDescription);
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public String getManufacturer() {
    return manufacturer.getValue();
  }

  public void setManufacturer(Manufacturer manufacturer) {
    this.manufacturer = manufacturer;
  }

  public URI getManufacturerURI() {
    return this.manufacturer.getURI();
  }

  public Integer getQuantity() {
    return this.quantity.getValue();
  }

  public void setQuantity(Integer quantity) {
    this.quantity = new Quantity(quantity);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((manufacturer == null) ? 0 : manufacturer.hashCode());
    result = prime * result + ((partDescription == null) ? 0 : partDescription.hashCode());
    result = prime * result + ((partName == null) ? 0 : partName.hashCode());
    result = prime * result + ((product == null) ? 0 : product.hashCode());
    result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Part other = (Part) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (manufacturer == null) {
      if (other.manufacturer != null)
        return false;
    } else if (!manufacturer.equals(other.manufacturer))
      return false;
    if (partDescription == null) {
      if (other.partDescription != null)
        return false;
    } else if (!partDescription.equals(other.partDescription))
      return false;
    if (partName == null) {
      if (other.partName != null)
        return false;
    } else if (!partName.equals(other.partName))
      return false;
    if (product == null) {
      if (other.product != null)
        return false;
    } else if (!product.equals(other.product))
      return false;
    if (quantity == null) {
      if (other.quantity != null)
        return false;
    } else if (!quantity.equals(other.quantity))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Part [id=" + id + ", manufacturer=" + manufacturer + ", partDescription=" + partDescription + ", partName="
        + partName + ", product=" + product + ", quantity=" + quantity + "]";
  }

}