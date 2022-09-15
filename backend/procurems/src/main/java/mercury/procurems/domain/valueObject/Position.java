package mercury.procurems.domain.valueObject;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import mercury.procurems.domain.exceptions.EmptyStringException;

@Embeddable
public class Position {

  @Column(name = "POSITION", unique = false, nullable = false, length = 100)
  private String value;

  public Position() {}

  public Position(String value) {
    try {
      if (value.length() == 0) {
        throw new EmptyStringException("Position");
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
    if (!(o instanceof Position)) {
      return false;
    } else {
      Position pp = (Position) o;
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
