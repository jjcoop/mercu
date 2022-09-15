package mercury.inventoryms.domain.valueObject;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import mercury.inventoryms.domain.exceptions.EmptyStringException;

@Embeddable
public class PartDescription {

  @Column(
    name = "PART_DESCRIPTION",
    unique = false,
    nullable = false,
    length = 100
  )
  private String value;

  PartDescription() {}

  public PartDescription(String value) {
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
    if (!(o instanceof PartDescription)) {
      return false;
    } else {
      PartDescription pp = (PartDescription) o;
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
