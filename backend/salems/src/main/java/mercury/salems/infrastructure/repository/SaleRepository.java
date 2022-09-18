package mercury.salems.infrastructure.repository;

import mercury.salems.domain.aggregate.Sale;
import mercury.salems.domain.aggregate.OnlineSale;
import mercury.salems.domain.aggregate.InStoreSale;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface SaleRepository<T extends Sale> extends JpaRepository<Sale, Long> {
    @Query("from OnlineSale")
    List<OnlineSale> findAllOnlineSales();
    @Query("from OnlineSale")
    OnlineSale findOnlineSaleById(Long id);

    @Query("from InStoreSale")
    List<InStoreSale> findAllInStoreSales();
    @Query("from InStoreSale")
    InStoreSale findInStoreSaleById(Long id);
}