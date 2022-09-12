package mercury.inventoryms.domain.valueObject;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import mercury.inventoryms.domain.exceptions.EmptyStringException;

@Embeddable
public class PartName {

  @Column(
    name = "PART_NAME",
    unique = false,
    nullable = false,
    updatable = false,
    length = 100
  )
  private String value;

  public PartName() {}

  public PartName(String value) {
    try {
      if (value.length() == 0) {
        throw new EmptyStringException("Part name");
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
    if (!(o instanceof PartName)) {
      return false;
    } else {
      PartName pp = (PartName) o;
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
