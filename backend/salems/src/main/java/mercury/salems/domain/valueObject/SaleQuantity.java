package mercury.salems.domain.valueObject;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import mercury.salems.domain.exceptions.InvalidQuantityException;

@Embeddable
public class SaleQuantity {

  @Column(
    name = "ADDRESS",
    unique = false,
    nullable = false,
    length = 100
  )
  private int value;

  public SaleQuantity() {}

  public SaleQuantity(int value) {
    try {
      if (value <= 0) {
        throw new InvalidQuantityException(value);
      }
      this.value = value;
    } catch (InvalidQuantityException e) {
      System.out.println(e.getMessage());
    }
  }

  public int getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof SaleQuantity)) {
      return false;
    } else {
      SaleQuantity pp = (SaleQuantity) o;
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
