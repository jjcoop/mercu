package mercury.inventoryms.domain.valueObject.exceptions;

public class EmptyStringException  extends Exception {
    public EmptyStringException(String objectDescription) {
        super(objectDescription + " must have a valid, non empty value!");
    }
}
