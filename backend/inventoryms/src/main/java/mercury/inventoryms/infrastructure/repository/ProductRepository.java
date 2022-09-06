package mercury.inventoryms.infrastructure.repository;

import mercury.inventoryms.domain.aggregate.Product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByName(String name);
}