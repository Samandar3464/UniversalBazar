package uz.pdp.bazar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.bazar.entity.FireBaseToken;

public interface TokenRepository extends JpaRepository<FireBaseToken, Integer> {
}
