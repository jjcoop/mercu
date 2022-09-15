package mercury.procurems.domain.valueObject;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import mercury.procurems.domain.exceptions.EmptyStringException;

@Embeddable
public class CompanyName {

  @Column(name = "COMPANY_NAME", unique = false, nullable = false, length = 100)
  private String value;

  public CompanyName() {}

  public CompanyName(String value) {
    try {
      if (value.length() == 0) {
        throw new EmptyStringException("Company name");
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
    if (!(o instanceof CompanyName)) {
      return false;
    } else {
      CompanyName pp = (CompanyName) o;
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
