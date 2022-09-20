package mercury.inventoryms.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mercury.inventoryms.domain.aggregate.Part;

public interface PartRepository extends JpaRepository<Part, Long> {
}