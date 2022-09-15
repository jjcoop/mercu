package mercury.salems.domain.valueObject;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SaleDate {

  @Column(
    name = "DATE",
    unique = false,
    nullable = false,
    updatable = false,
    length = 100
  )
  private Date value;

  public SaleDate() {}

  public SaleDate(Date value) {
    this.value = value;
  }

  public Date getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof SaleDate)) {
      return false;
    } else {
      SaleDate pp = (SaleDate) o;
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
