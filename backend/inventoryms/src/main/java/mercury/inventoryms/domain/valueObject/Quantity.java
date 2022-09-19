package mercury.inventoryms.domain.valueObject;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import mercury.inventoryms.domain.valueObject.exceptions.ValueOutsideRangeException;

@Embeddable
public class Quantity {

  @Column(
    name = "QUANTITY",
    unique = false,
    nullable = false,
    length = 100
  )
  private Integer value;

  public Quantity() {}

  public Quantity(Integer value) {
    try {
      if (value < 0) {
        throw new ValueOutsideRangeException(value);
      }
      this.value = value;
    } catch (ValueOutsideRangeException e) {
      System.out.println(e.getMessage());
    }
  }

  public Integer getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Quantity)) {
      return false;
    } else {
      Quantity pq = (Quantity) o;
      if (this.value == pq.getValue()) {
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
