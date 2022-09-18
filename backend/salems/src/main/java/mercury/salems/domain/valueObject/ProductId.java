package mercury.salems.domain.valueObject;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductId {

  @Column(
    name = "PRODUCT_ID",
    unique = false,
    nullable = false,
    length = 100
  )
  private int value;

  public ProductId() {}

  public ProductId(int value) {
      this.value = value;

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
