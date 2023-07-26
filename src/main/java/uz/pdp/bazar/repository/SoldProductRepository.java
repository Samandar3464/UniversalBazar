package uz.pdp.bazar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.bazar.entity.SoldProduct;
import uz.pdp.bazar.entity.Test;


import java.time.LocalDateTime;

import java.util.UUID;

public interface SoldProductRepository extends JpaRepository<SoldProduct , UUID> {

    Page<SoldProduct> findAllByMarketIdAndSoldDateBetween(Integer marketId, LocalDateTime startDate , LocalDateTime endDate, Pageable pageable);

   @Query(nativeQuery = true,value = "select s.product_id  , count (s.price) , count (s.quantity)  from sold_product s where s.market_id =?1 and s.sold_date > ?2 and s.sold_date < ?3 group by s.product_id")
   Page<Test> getAll(Integer marketId, LocalDateTime startDate , LocalDateTime endDate, Pageable pageable);
}
