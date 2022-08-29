package mercury.procurems.domain.aggregate;

import java.util.Set;
import mercury.procurems.domain.entity.Contact;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Tbl_Supplier")
public class Supplier {
  @Id
  @Column(name = "ID", unique = true, nullable = false)
  @GeneratedValue
  private Long id;
  @Column(name = "COMPANY_NAME", unique = false, nullable = false, length = 100)
  private String companyName;
  @Column(name = "BASE", unique = false, nullable = false, length = 100)
  private String base;
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "SUPPLIER")
  private Set<Contact> contacts;

  Supplier() {
  }

  public Supplier(String companyName, String base) {

    this.companyName = companyName;
    this.base = base;
  }

}