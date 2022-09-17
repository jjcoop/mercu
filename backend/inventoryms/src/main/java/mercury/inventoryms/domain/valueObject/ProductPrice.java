package mercury.inventoryms.domain.valueObject;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import mercury.inventoryms.domain.exceptions.ValueOutsideRangeException;

@Embeddable
public class ProductPrice {

  @Column(
    name = "PRICE",
    unique = false,
    nullable = false,
    length = 100
  )
  private double value;

  public ProductPrice() {}

  public ProductPrice(double value) {
    try {
      if (value < 0) {
        throw new ValueOutsideRangeException(value);
      }
      this.value = value;
    } catch (ValueOutsideRangeException e) {
      System.out.println(e.getMessage());
    }
  }

  public double getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof ProductPrice)) {
      return false;
    } else {
      ProductPrice pp = (ProductPrice) o;
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
