package mercury.salems.domain.valueObject;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import mercury.salems.domain.exceptions.EmptyStringException;

@Embeddable
public class CustomerAddress {

  @Column(
    name = "CUST_ADDRESS",
    unique = false,
    nullable = true,
    length = 100
  )
  private String value;

  public CustomerAddress() {}

  public CustomerAddress(String value) {
    try {
      if (value.length() == 0) {
        throw new EmptyStringException("Customer address");
      }
      this.value = value;
    } catch (EmptyStringException e) {
      System.out.println(e.getMessage());
    }
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof CustomerAddress)) {
      return false;
    } else {
      CustomerAddress pp = (CustomerAddress) o;
      if (this.value == pp.getValue()) {
        return true;
      } else {
        return false;
      }
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.value);
  }
}
