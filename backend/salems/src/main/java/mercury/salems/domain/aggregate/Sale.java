package mercury.salems.domain.aggregate;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "Tbl_Sale")
@SequenceGenerator(name="sal", initialValue=9999900, allocationSize=100)
public class Sale {
  
  @Column(name = "ID", unique = true, nullable = false)
  @GeneratedValue(strategy=GenerationType.IDENTITY, generator="sal")
  @Id Long id;

  @Column(name = "PRODUCT", unique = false, nullable = false, length = 100)
  private String productName;
  @Column(name = "QUANTITY", unique = false, nullable = false, length = 100)
  private int quantity;
  @Column(name = "DATE", unique = false, nullable = false, length = 100)
  private Date dateTime = new Date();


  public Sale(){}


  public Sale(Long id, String productName, int quantity) {
    this.id = id;
    this.productName = productName;
    this.quantity = quantity;
}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public Date getDateTime() {
    return dateTime;
  }

  public void setDateTime(Date dateTime) {
    this.dateTime = dateTime;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((productName == null) ? 0 : productName.hashCode());
    result = prime * result + quantity;
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
    Sale other = (Sale) obj;
    if (dateTime == null) {
      if (other.dateTime != null)
        return false;
    } else if (!dateTime.equals(other.dateTime))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (productName == null) {
      if (other.productName != null)
        return false;
    } else if (!productName.equals(other.productName))
      return false;
    if (quantity != other.quantity)
      return false;
    return true;
  }
}

