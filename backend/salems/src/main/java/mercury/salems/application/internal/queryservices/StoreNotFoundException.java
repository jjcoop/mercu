package mercury.salems.application.internal.queryservices;

public class StoreNotFoundException extends RuntimeException {

    public StoreNotFoundException(Long id) {
      super("Could not find store " + id);
    }
  }