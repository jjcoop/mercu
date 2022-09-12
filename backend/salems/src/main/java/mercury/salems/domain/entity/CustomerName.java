package mercury.salems.domain.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import mercury.salems.domain.exceptions.EmptyStringException;

@Embeddable
public class CustomerName {

  @Column(
    name = "CUSTOMER",
    unique = false,
    nullable = true,
    updatable = false,
    length = 100
  )
  private String value;

  public CustomerName() {}

  public CustomerName(String value) {
    try {
      if (value.length() == 0) {
        throw new EmptyStringException("Customer name");
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
    if (!(o instanceof CustomerName)) {
      return false;
    } else {
      CustomerName pp = (CustomerName) o;
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
