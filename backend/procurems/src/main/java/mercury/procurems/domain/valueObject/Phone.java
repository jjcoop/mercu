package mercury.procurems.domain.valueObject;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import mercury.procurems.domain.exceptions.EmptyStringException;

@Embeddable
public class Phone {

  @Column(name = "PHONE", unique = false, nullable = false, length = 100)
  private String value;

  public Phone() {}

  public Phone(String value) {
    try {
      if (value.length() == 0) {
        throw new EmptyStringException("Phone");
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
    if (!(o instanceof Phone)) {
      return false;
    } else {
      Phone pp = (Phone) o;
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
