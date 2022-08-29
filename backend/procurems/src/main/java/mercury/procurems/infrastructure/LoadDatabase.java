package mercury.procurems.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mercury.procurems.domain.aggregate.Supplier;
import mercury.procurems.infrastructure.repository.SupplierRepository;


@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(SupplierRepository repository) {

    return args -> {
      log.info("Load Database: Production");
      log.info("Preloading " + repository.save(new Supplier("Bent Spokes", "Canberra")));
      // log.info("Preloading " + repository.save(new Supplier("Chain Mail", "Sydney")));
      // log.info("Preloading " + cRepo.save(new Contact("bob", "lee", "0402344444", "bob@mercury.com.au", "manger")));
    };
  }
}
