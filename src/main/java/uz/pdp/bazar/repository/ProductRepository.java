package uz.pdp.bazar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.bazar.entity.Market;
import uz.pdp.bazar.entity.Product;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findAllByMarketIdAndActiveTrueAndDeletedFalseOrderByQuantityDesc(Integer marketId, Pageable pageable);

    Page<Product> findAllByQuantityGreaterThanAndMarketIdAndActiveTrueAndDeletedFalse(double quantity, Integer marketId, Pageable pageable);

    Page<Product> findAllByQuantityGreaterThanAndActiveTrueAndDeletedFalseOrderByCreatedDateDesc(double quantity, Pageable pageable);
    Page<Product> findAllByQuantityGreaterThanAndMarketInAndActiveTrueAndDeletedFalseOrderByCreatedDateDesc(double quantity, List<Market> activeMarkets, Pageable pageable);

    Page<Product> findAllByQuantityGreaterThanAndActiveFalseAndDeletedFalse(double quantity, Pageable pageable);

    boolean existsByMarketIdAndMeasurementIdAndName(Integer marketId, Integer measurementId, String name);

}
