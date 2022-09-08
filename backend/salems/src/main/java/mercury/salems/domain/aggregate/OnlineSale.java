package mercury.salems.domain.aggregate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class OnlineSale extends Sale {
    @Column(name = "CUSTOMER", unique = false, nullable = true, length = 100)
    private String customerName;
    @Column(name = "ADDRESS", unique = false, nullable = true, length = 100)
    private String address;
}
