package uz.pdp.bazar.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.bazar.entity.Measurement;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement,Integer> {

    List<Measurement> findAllByActiveTrue();

    boolean existsByNameAndActiveTrue(String name);
}
