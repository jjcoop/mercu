package mercury.procurems.domain.aggregate;
import mercury.procurems.domain.entity.Contact;

import java.util.Collections;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
  @Embedded
  private Set<Contact> contacts =  Collections.emptySet();

  Supplier() {
  }

  public Supplier(String companyName, String base) {

    this.companyName = companyName;
    this.base = base;
    this.contacts = getContacts();

  }

  public Long getId() {
    return this.id;
  }

  public String getCompanyName() {
    return this.companyName;
  }

  public String getBase() {
    return this.base;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public void setBase(String base) {
    this.base = base;
  }

  public Set<Contact> getContacts() {
    return contacts;
  }

  public void setContacts(Set<Contact> contacts) {
    this.contacts = contacts;
  }

  public void addContact(Contact contact) {
    this.contacts.add(contact);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Supplier))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Supplier [base=" + base + ", companyName=" + companyName + ", contacts=" + contacts + ", id=" + id + "]";
  }

}