package mercury.procurems.domain.valueObject;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import mercury.procurems.domain.exceptions.EmptyStringException;

@Embeddable
public class Email {

  @Column(name = "EMAIL", unique = false, nullable = false, length = 100)
  private String value;

  public Email() {}

  public Email(String value) {
    try {
      if (value.length() == 0) {
        throw new EmptyStringException("Email");
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
    if (!(o instanceof Email)) {
      return false;
    } else {
      Email pp = (Email) o;
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
