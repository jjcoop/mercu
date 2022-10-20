package mercury.salems.domain.valueObject;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import mercury.salems.domain.exceptions.InvalidQuantityException;

@Embeddable
public class SaleTotal {

  @Column(
    name = "TOTAL",
    unique = false,
    nullable = false,
    length = 20
  )
  private Double value = 0.0;

  public SaleTotal() {}

  public SaleTotal(Double value) {
    try {
      if (value <= 0) {
        throw new InvalidQuantityException(value);
      }
      this.value = value;
    } catch (InvalidQuantityException e) {
      System.out.println(e.getMessage());
    }
  }

  public Double getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof SaleTotal)) {
      return false;
    } else {
      SaleTotal pp = (SaleTotal) o;
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
