package mercury.salems.domain.valueObject;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import mercury.salems.domain.exceptions.EmptyStringException;

@Embeddable
public class SaleProductName {

  @Column(
    name = "PRODUCT",
    unique = false,
    nullable = false,
    length = 100
  )
  private String value;

  public SaleProductName() {}

  public SaleProductName(String value) {
    try {
      if (value.length() == 0) {
        throw new EmptyStringException("Sale product name");
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
    if (!(o instanceof SaleProductName)) {
      return false;
    } else {
      SaleProductName pp = (SaleProductName) o;
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
