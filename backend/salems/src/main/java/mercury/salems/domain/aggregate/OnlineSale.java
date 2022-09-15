package mercury.salems.domain.aggregate;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import mercury.salems.domain.valueObject.CustomerAddress;
import mercury.salems.domain.valueObject.CustomerName;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class OnlineSale extends Sale {
    @Embedded
    private CustomerName customerName;
    @Embedded
    private CustomerAddress address;

    public OnlineSale(Long id, String productName, int quantity, String customerName, String address) {
        super(id, productName, quantity);
        this.customerName = new CustomerName(customerName);
        this.address = new CustomerAddress(address);
    }
}
