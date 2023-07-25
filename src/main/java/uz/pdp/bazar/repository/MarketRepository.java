package uz.pdp.bazar.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.bazar.entity.Market;

import java.util.List;
import java.util.Optional;

public interface MarketRepository extends JpaRepository<Market, Integer> {

    Optional<Market> findByUserIdAndName(Integer userId, String name);
    Optional<Market> findByName(String name);

    List<Market> findAllByActiveTrueAndDeleteFalse();
    Optional<Market> findAllByUserIdAndDeleteFalse(Integer userId);

    Page<Market> findAllByDeleteFalse(Pageable pageable);
}
