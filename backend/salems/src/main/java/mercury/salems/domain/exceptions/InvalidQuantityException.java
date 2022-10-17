package mercury.salems.domain.exceptions;

public class InvalidQuantityException  extends Exception {
    public InvalidQuantityException(int value) {
        super("Current sale quantity (value : " + value + ") is less than minimum value of 1.");
    }
    public InvalidQuantityException(Double value) {
        super("Current sale total (value : " + value + ") is less than minimum value of 1.");
    }
}
