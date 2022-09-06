package mercury.salems.infrastructure.repository;

import mercury.salems.domain.entity.Store;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findAll();
}