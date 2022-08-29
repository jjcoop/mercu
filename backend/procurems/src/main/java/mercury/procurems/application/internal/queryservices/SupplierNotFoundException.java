package mercury.procurems.application.internal.queryservices;

class SupplierNotFoundException extends RuntimeException {

    SupplierNotFoundException(Long id) {
      super("Could not find supplier " + id);
    }
  }