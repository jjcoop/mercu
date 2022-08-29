package mercury.procurems.infrastructure.repository;

import mercury.procurems.domain.aggregate.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

}