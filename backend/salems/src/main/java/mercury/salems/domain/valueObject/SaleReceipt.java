package mercury.salems.domain.valueObject;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import mercury.salems.domain.exceptions.EmptyStringException;

@Embeddable
public class SaleReceipt {

  @Column(
    name = "RECEIPT",
    unique = false,
    nullable = true,
    length = 100
  )
  private String value;

  public SaleReceipt() {}

  public SaleReceipt(String value) {
    try {
      if (value.length() == 0) {
        throw new EmptyStringException("Receipt ID");
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
    if (!(o instanceof SaleReceipt)) {
      return false;
    } else {
      SaleReceipt pp = (SaleReceipt) o;
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
