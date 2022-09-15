package mercury.procurems.domain.aggregate;
import mercury.procurems.domain.entity.Contact;
import mercury.procurems.domain.valueObject.CompanyBase;
import mercury.procurems.domain.valueObject.CompanyName;

import java.util.Collections;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Tbl_Supplier")
@SequenceGenerator(name="sup", initialValue=2386, allocationSize=100)
public class Supplier {
  
  @Column(name = "ID", unique = true, nullable = false)
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sup")
  @Id Long id;

  @Embedded
  private CompanyName companyName;
  @Embedded
  private CompanyBase base;
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "SUPPLIER")
  @Embedded
  private Set<Contact> contacts =  Collections.emptySet();

  Supplier() {
  }

  public Supplier(String companyName, String base) {

    this.companyName = new CompanyName(companyName);
    this.base = new CompanyBase(base);
    this.contacts = getContacts();

  }

  public Long getId() {
    return this.id;
  }

  public String getCompanyName() {
    return this.companyName.getValue();
  }

  public String getBase() {
    return this.base.getValue();
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setCompanyName(String companyName) {
    this.companyName = new CompanyName(companyName);
  }

  public void setBase(String base) {
    this.base = new CompanyBase(base);
  }

  public Set<Contact> getContacts() {
    return contacts;
  }

  public void updateContact(Long id, Contact contact) {
    boolean found = false;
    for (Contact c : contacts) {
      if (c.getId().equals(id)) {
        c.setFname(contact.getName().split(" ")[0]);
        c.setLname(contact.getName().split(" ")[1]);
        c.setEmail(contact.getEmail());
        c.setPhone(contact.getPhone());
        c.setPosition(contact.getPosition());
        found = true;
      }
    }
    
    if (!found) {
      boolean emailCheck = false;
      for (Contact c : contacts) {
        if (c.getEmail().equals(contact.getEmail())) {
          c.setFname(contact.getName().split(" ")[0]);
          c.setLname(contact.getName().split(" ")[1]);
          c.setEmail(contact.getEmail());
          c.setPhone(contact.getPhone());
          c.setPosition(contact.getPosition());
          emailCheck = true;
        }
      }
      if (!emailCheck) {
        Contact newContact = new Contact();
        newContact.setFname(contact.getName().split(" ")[0]);
        newContact.setLname(contact.getName().split(" ")[1]);
        newContact.setEmail(contact.getEmail());
        newContact.setPhone(contact.getPhone());
        newContact.setPosition(contact.getPosition());
        contact.setSupplier(this);
        this.contacts.add(contact);        
      }
    };
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
    return "Supplier [testeroni=" + base.getValue() + ", companyName=" + companyName.getValue() + ", contacts=" + contacts + ", id=" + id + "]";
  }

}