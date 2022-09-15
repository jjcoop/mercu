package mercury.inventoryms.domain.valueObject;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import mercury.inventoryms.domain.exceptions.EmptyStringException;

@Embeddable
public class ProductDescription {

  @Column(
    name = "DESCRIPTION",
    unique = false,
    nullable = false,
    updatable = false,
    length = 100
  )
  private String value;

  public ProductDescription(){}

  public ProductDescription(String value) {
    try {
      if (value.length() == 0) {
        throw new EmptyStringException("Part description");
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
    if (!(o instanceof ProductDescription)) {
      return false;
    } else {
        ProductDescription pp = (ProductDescription) o;
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
