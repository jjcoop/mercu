package mercury.salems.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mercury.salems.domain.aggregate.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findAll();
}