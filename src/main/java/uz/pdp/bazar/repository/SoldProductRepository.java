package uz.pdp.bazar.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.bazar.entity.SoldProduct;

import java.util.UUID;

public interface SoldProductRepository extends JpaRepository<SoldProduct , UUID> {
}
