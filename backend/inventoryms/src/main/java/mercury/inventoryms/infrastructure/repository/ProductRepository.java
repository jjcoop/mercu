package mercury.inventoryms.infrastructure.repository;

import mercury.inventoryms.domain.aggregate.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}