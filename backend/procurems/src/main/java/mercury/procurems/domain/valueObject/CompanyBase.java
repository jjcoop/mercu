package mercury.procurems.domain.valueObject;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import mercury.procurems.domain.exceptions.EmptyStringException;

@Embeddable
public class CompanyBase {

  @Column(name = "BASE", unique = false, nullable = false, length = 100)
  private String value;

  public CompanyBase() {}

  public CompanyBase(String value) {
    try {
      if (value.length() == 0) {
        throw new EmptyStringException("Company base");
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
    if (!(o instanceof CompanyBase)) {
      return false;
    } else {
      CompanyBase pp = (CompanyBase) o;
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
