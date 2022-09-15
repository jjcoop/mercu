package mercury.procurems.domain.entity;

import mercury.procurems.domain.aggregate.Supplier;
import mercury.procurems.domain.valueObject.Email;
import mercury.procurems.domain.valueObject.Fname;
import mercury.procurems.domain.valueObject.Lname;
import mercury.procurems.domain.valueObject.Phone;
import mercury.procurems.domain.valueObject.Position;

import mercury.procurems.interfaces.rest.SupplierController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.Link;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "Tbl_Contact")
@SequenceGenerator(name="con", initialValue=30423, allocationSize=100)
public class Contact {
  @Id @Column(name = "ID", unique = true, nullable = false) @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="con")
  private Long id;
  @Embedded
  private Fname fname;
  @Embedded
  private Lname lname;
  @Embedded
  private Phone phone;
  @Embedded
  private Email email;
  @Embedded
  private Position position;
  @ManyToOne(cascade=CascadeType.PERSIST)
  @JoinColumn(name = "SUPPLIER_ID")
  @JsonIgnore
  private Supplier supplier;

  public Contact() {}

  public Contact(String fname,String lname, String phone, String email, String position) {
    this.fname = new Fname(fname);
    this.lname = new Lname(lname);
    this.phone = new Phone(phone);
    this.email = new Email(email);
    this.position = new Position(position);
  }

  @JsonProperty(value = "supplier")
  public Link getSupplierName(){
    return linkTo(methodOn(SupplierController.class).one(supplier.getId())).withSelfRel();
  }
  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return fname.getValue() + " " + lname.getValue();
  }

  public void setFname(String fname) {
    this.fname = new Fname(fname);
  }

  public void setLname(String lname) {
    this.lname = new Lname(lname);
  }

  public String getPhone() {
    return phone.getValue();
  }

  public void setPhone(String phone) {
    this.phone = new Phone(phone);
  }

  public String getEmail() {
    return email.getValue();
  }

  public void setEmail(String email) {
    this.email = new Email(email);
  }

  public String getPosition() {
    return position.getValue();
  }

  public void setPosition(String position) {
    this.position = new Position(position);
  }

  public Supplier getSupplier() {
    return supplier;
  }

  public void setSupplier(Supplier supplier) {
    this.supplier = supplier;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof Contact))
      return false;
    Contact Contact = (Contact) o;
    return Objects.equals(this.id, Contact.id) && Objects.equals(this.fname, Contact.fname)
        && Objects.equals(this.lname, Contact.lname)
        && Objects.equals(this.phone, Contact.phone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.fname.getValue(), this.lname.getValue(), this.phone.getValue());
  }

  @Override
  public String toString() {
    return "Contact [email=" + email.getValue() + ", fname=" + fname.getValue() + ", id=" + id + ", lname=" + lname.getValue() + ", phone=" + phone.getValue()
        + ", position=" + position.getValue() + ", supplier=" + supplier + "]";
  }

  // @Override
  // public String toString() {
  //   return "Contact [email=" + email + ", fname=" + fname + ", lname=" + lname + ", phone=" + phone
  //       + ", position=" + position + ", id=" + id + "";
  // }

  
}