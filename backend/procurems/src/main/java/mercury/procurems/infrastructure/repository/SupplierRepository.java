package mercury.procurems.infrastructure.repository;

import mercury.procurems.domain.aggregate.Supplier;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    // Cargo findByBookingId(String BookingId);

    // List<BookingId> findAllBookingIds();

    List<Supplier> findAll();
}