package mercury.salems.domain.aggregate;

import mercury.salems.domain.valueObject.StoreAddress;
import mercury.salems.domain.valueObject.StoreManager;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.Embedded;
import java.util.Collections;

import javax.persistence.FetchType;

@Entity
@Table(name = "Tbl_Store")
@SequenceGenerator(name = "sto", initialValue = 33333200, allocationSize = 100)
public class Store {
  @Id
  @Column(name = "ID", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sto")
  private Long id;
  @Embedded
  private StoreAddress address;
  @Embedded
  private StoreManager managerName;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "SALES")
  @Embedded
  private Set<InStoreSale> sales = Collections.emptySet();

  Store() {}

  public Store(Long id, String address, String managerName) {
    this.id = id;
    this.address = new StoreAddress(address);
    this.managerName = new StoreManager(managerName);
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAddress() {
    return address.getValue();
  }

  public void setAddress(String address) {
    this.address = new StoreAddress(address);
  }

  public String getManagerName() {
    return managerName.getValue();
  }

  public void setManagerName(String managerName) {
    this.managerName = new StoreManager(managerName);
  }

  public Set<InStoreSale> getSales() {
    return sales;
  }

  public void setSales(Set<InStoreSale> sales) {
    this.sales = sales;
  }

  public void addSale(InStoreSale sale) {
    this.sales.add(sale);
  }

  @Override
  public boolean equals(Object o) {

  if (this == o)
  return true;
  if (!(o instanceof Store))
  return false;
  Store store = (Store) o;
  return Objects.equals(this.id, store.id)
  && Objects.equals(this.address, store.address)
  && Objects.equals(this.managerName, store.managerName);
  }

  @Override
  public int hashCode() {
  return Objects.hash(this.id, this.address, this.managerName);
  }

  @Override
  public String toString() {
  return "Store [address=" + address + ", manager=" + managerName + ",id=" + id + ", sales=" + sales + "]";
  }

}