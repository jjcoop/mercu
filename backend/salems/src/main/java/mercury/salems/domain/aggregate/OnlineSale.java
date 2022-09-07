package mercury.salems.domain.aggregate;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class OnlineSale extends Sale {
    @Column(name = "CUSTOMER", unique = false, nullable = false, length = 100)
    private String customerName;
    @Column(name = "ADDRESS", unique = false, nullable = false, length = 100)
    private String address;
}
