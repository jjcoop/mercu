package mercury.salems.domain.aggregate;

import javax.persistence.InheritanceType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.Link;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import mercury.salems.domain.entity.Store;
import mercury.salems.interfaces.rest.SaleController;
import net.bytebuddy.utility.RandomString;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class InStoreSale extends Sale{
    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "STORE_ID")
    @JsonIgnore
    private Store store;
    @Column(name = "RECEIPT", unique = false, nullable = false, length = 100)
    private String receipt = RandomString.make();
  
    @JsonProperty(value = "store")
    public Link getStoreName(){
      return linkTo(methodOn(SaleController.class).getStore(this.store.getId())).withSelfRel();
    }

    public Store getStore() {
      return store;
    }

    public void setStore(Store store) {
      this.store = store;
    }

    public String getReceipt() {
      return receipt;
    }

    public void setReceipt(String receipt) {
      this.receipt = receipt;
    }
  }
