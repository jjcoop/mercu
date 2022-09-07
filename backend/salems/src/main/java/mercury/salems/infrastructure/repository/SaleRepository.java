package mercury.salems.infrastructure.repository;

import mercury.salems.domain.aggregate.Sale;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findAll();
}