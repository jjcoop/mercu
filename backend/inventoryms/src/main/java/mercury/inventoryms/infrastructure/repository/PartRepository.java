package mercury.inventoryms.infrastructure.repository;

import mercury.inventoryms.domain.aggregate.Part;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part, Long> {
    // Cargo findByBookingId(String BookingId);

    // List<BookingId> findAllBookingIds();

    List<Part> findAll();
}