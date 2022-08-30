package mercury.procurems.domain.entity;

import mercury.procurems.domain.aggregate.Supplier;
import mercury.procurems.interfaces.rest.SupplierController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.Link;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
  @Column(name = "FNAME", unique = false, nullable = false, length = 100)
  private String fname;
  @Column(name = "LNAME", unique = false, nullable = false, length = 100)
  private String lname;
  @Column(name = "PHONE", unique = false, nullable = false, length = 100)
  private String phone;
  @Column(name = "EMAIL", unique = false, nullable = false, length = 100)
  private String email;
  @Column(name = "POSITION", unique = false, nullable = false, length = 100)
  private String position;
  @ManyToOne(cascade=CascadeType.PERSIST)
  @JoinColumn(name = "SUPPLIER_ID")
  @JsonIgnore
  private Supplier supplier;

  Contact() {}

  public Contact(String fname,String lname, String phone, String email, String position) {
    this.fname = fname;
    this.lname = lname;
    this.phone = phone;
    this.email = email;
    this.position = position;
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
    return fname + " " + lname;
  }

  public void setFname(String fname) {
    this.fname = fname;
  }

  public void setLname(String lname) {
    this.lname = lname;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
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
    return Objects.hash(this.id, this.fname, this.lname, this.phone);
  }

  @Override
  public String toString() {
    return "Contact [email=" + email + ", fname=" + fname + ", id=" + id + ", lname=" + lname + ", phone=" + phone
        + ", position=" + position + ", supplier=" + supplier + "]";
  }

  // @Override
  // public String toString() {
  //   return "Contact [email=" + email + ", fname=" + fname + ", lname=" + lname + ", phone=" + phone
  //       + ", position=" + position + ", id=" + id + "";
  // }

  
}