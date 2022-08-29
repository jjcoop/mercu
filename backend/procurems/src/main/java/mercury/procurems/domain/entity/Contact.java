package mercury.procurems.domain.entity;


import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnore;

import mercury.procurems.domain.aggregate.Supplier;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "Tbl_Contact")
public class Contact {
  @Id @Column(name = "ID", unique = true, nullable = false) @GeneratedValue
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
}