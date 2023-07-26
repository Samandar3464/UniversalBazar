package uz.pdp.bazar.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {

    @Query(nativeQuery = true, value = "select s.product_id  , count (s.quantity) , count (s.price) " +
            "from sold_product s where s.market_id =?1 and s.sold_date > ?2 and s.sold_date < ?3 group by s.product_id")
    Page<Test> getAll(Integer marketId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

}