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
  @GeneratedValue(strategy=GenerationType.TABLE, generator="sal")
  @Id Long id;

  @Column(name = "PRODUCT", unique = false, nullable = false, length = 100)
  private String productName;
  @Column(name = "QUANTITY", unique = false, nullable = false, length = 100)
  private int quantity;
  @Column(name = "DATE", unique = false, nullable = false, length = 100)
  private Date dateTime;


  public Sale(){}

  public Sale(String productName, int quantity, Date dateTime) {

    this.productName = productName;
    this.quantity = quantity;
    this.dateTime = dateTime;
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



  // @Override
  // public boolean equals(Object o) {
  //   if (this == o)
  //     return true;
  //   if (!(o instanceof Product))
  //     return false;
  //   return true;
  // }

  // @Override
  // public String toString() {
  //   return "Product [name=" + name + ", price=" + price + ", description=" + description + ", parts=" + parts + ", id=" + id + "]";
  // }
  
}

