package mercury.inventoryms.domain.valueObject.exceptions;

public class ValueOutsideRangeException  extends Exception {
    public ValueOutsideRangeException(double value) {
        super("Current value : " + value + " is less than minimum value of 0.");
    }
}
