package mercury.salems.domain.aggregate;

import java.net.URI;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.domain.AbstractAggregateRoot;

import mercury.salems.domain.valueObject.SaleDate;
import mercury.salems.domain.valueObject.SaleProductName;
import mercury.salems.domain.valueObject.SaleQuantity;
import mercury.salems.domain.valueObject.SaleTotal;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "Tbl_Sale")
@SequenceGenerator(name = "sal", initialValue = 9999900, allocationSize = 2000)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Sale extends AbstractAggregateRoot<Sale> {

  @Column(name = "ID", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sal")
  @Id
  Long id;

  private String orderStatus = "PROCESSING";

  @Embedded
  private SaleProductName productName;

  @Embedded
  private SaleQuantity quantity;

  @Embedded
  private SaleTotal total;

  @Embedded
  private SaleDate dateTime = new SaleDate(new Date());

  public Sale() {}

  public Sale(Long id, String productName, int quantity) {
    this.id = id;
    this.productName = new SaleProductName(productName);
    this.quantity = new SaleQuantity(quantity);  
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  public String getProductName() {
    return productName.getValue();
  }

  public void setProductName(SaleProductName productName) {
    this.productName = productName;
  }

  public URI getproductURI() {
    return productName.getURI();
  }

  public int getQuantity() {
    return quantity.getValue();
  }

  public void setQuantity(int quantity) {
    this.quantity = new SaleQuantity(quantity);
  }

  public Double getTotal() {
    return total.getValue();
  }

  public void setTotal(Double total) {
    this.total = new SaleTotal(total);
  }

  public Date getDateTime() {
    return dateTime.getValue();
  }

  public void setDateTime(Date dateTime) {
    this.dateTime = new SaleDate(dateTime);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
      prime *
      result +
      ((dateTime.getValue() == null) ? 0 : dateTime.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result =
      prime *
      result +
      ((productName.getValue() == null) ? 0 : productName.hashCode());
    result = prime * result + quantity.getValue();
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Sale other = (Sale) obj;
    if (dateTime == null) {
      if (other.dateTime != null) return false;
    } else if (
      !dateTime.getValue().equals(other.dateTime.getValue())
    ) return false;
    if (id == null) {
      if (other.id != null) return false;
    } else if (!id.equals(other.id)) return false;
    if (productName == null) {
      if (other.productName != null) return false;
    } else if (
      !productName.getValue().equals(other.productName.getValue())
    ) return false;
    if (quantity.getValue() != other.quantity.getValue()) return false;
    return true;
  }

  public void backOrder(){
    this.orderStatus = "ONBACKORDER";   
  }
}
