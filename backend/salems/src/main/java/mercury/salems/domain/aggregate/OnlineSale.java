package mercury.salems.domain.aggregate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import mercury.salems.domain.valueObject.CustomerAddress;
import mercury.salems.domain.valueObject.CustomerName;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value = "online")
public class OnlineSale extends Sale {
    @Embedded
    private CustomerName customerName;
    @Embedded
    private CustomerAddress address;

    public OnlineSale() {
        // to satisfy all of the requirements of each variable having a value
        this(new Long(1), "default", new Long(1), 1, "default", "default");
    }

    public OnlineSale(Long id, String productName, Long productId, int quantity, String customerName, String address) {
        super(id, productName, quantity);
        this.customerName = new CustomerName(customerName);
        this.address = new CustomerAddress(address);
    }

    public void setCustomerName(String value) {
        customerName = new CustomerName(value);
    }
    public String getCustomerName() {
        return customerName.getValue();
    }

    public void setAddress(String value) {
        address = new CustomerAddress(value);
    }
    public String getAddress() {
        return address.getValue();
    }
    
}
