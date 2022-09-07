package mercury.procurems.application.internal.queryservices;

public class SupplierNotFoundException extends RuntimeException {

    public SupplierNotFoundException(Long id) {
      super("Could not find supplier " + id);
    }
  }