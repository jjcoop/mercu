package mercury.salems.domain.aggregate;

import javax.persistence.InheritanceType;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.Link;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import mercury.salems.domain.valueObject.SaleReceipt;
import mercury.salems.interfaces.rest.SaleController;
import net.bytebuddy.utility.RandomString;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value = "instore")
public class InStoreSale extends Sale {
  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "STORE_ID")
  @JsonIgnore
  private Store store;

  @Embedded
  private SaleReceipt receipt = new SaleReceipt(RandomString.make());

  public InStoreSale() {
    // to satisfy all of the requirements of each variable having a value
    this(Long.parseLong("1"), "default", 1, "default");
  }

  public InStoreSale(Long id, String productName, int quantity, String receipt) {
    super(id, productName, quantity);
    this.receipt = new SaleReceipt(receipt);
  }

  @JsonProperty(value = "store")
  public Link getStoreName() {
    return linkTo(methodOn(SaleController.class).getStore(this.store.getId())).withSelfRel();
  }

  public Store getStore() {
    return store;
  }

  public void setStore(Store store) {
    this.store = store;
  }

  public Long getStoreId() {
    return store.getId();
  }

  public String getReceipt() {
    return receipt.getValue();
  }

  public void setReceipt(String receipt) {
    this.receipt = new SaleReceipt(receipt);
  }
}
