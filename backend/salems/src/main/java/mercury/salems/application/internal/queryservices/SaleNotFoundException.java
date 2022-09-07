package mercury.salems.application.internal.queryservices;

public class SaleNotFoundException extends RuntimeException {

    public SaleNotFoundException(Long id) {
      super("Could not find sale " + id);
    }
  }