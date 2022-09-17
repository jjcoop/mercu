package mercury.inventoryms.infrastructure.repository;

import mercury.inventoryms.domain.entity.Part;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part, Long> {
}