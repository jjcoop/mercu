package mercury.inventoryms.application.internal.queryservices;

public class PartNotFoundException extends RuntimeException {

    public PartNotFoundException(Long id) {
      super("Could not find part " + id);
    }
  }