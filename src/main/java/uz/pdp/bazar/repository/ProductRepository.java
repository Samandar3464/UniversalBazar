package uz.pdp.bazar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.bazar.entity.Product;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findAllByBranchId(Integer branchId, Pageable pageable);

    boolean existsByBranchIdAndMeasurementIdAndName(Integer branchId, Integer measurementId, String name);

}
