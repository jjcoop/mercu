package mercury.salems.infrastructure.repository;

import mercury.salems.domain.aggregate.OnlineSale;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineSaleRepository extends JpaRepository<OnlineSale, Long> {
    List<OnlineSale> findAll();
}