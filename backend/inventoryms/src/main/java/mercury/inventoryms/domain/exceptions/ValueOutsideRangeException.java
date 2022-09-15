package mercury.inventoryms.domain.exceptions;

public class ValueOutsideRangeException  extends Exception {
    public ValueOutsideRangeException(double value) {
        super("Current value : " + value + " is less than minimum value of 0.");
    }
}
