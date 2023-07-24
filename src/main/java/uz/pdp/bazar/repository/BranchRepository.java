package uz.pdp.bazar.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.bazar.entity.Branch;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Integer> {

    Optional<Branch> findByUserIdAndName(Integer userId, String name);
    Optional<Branch> findByName(String name);
    Optional<Branch> findAllByUserIdAndDeleteFalse(Integer userId);

    Page<Branch> findAllByDeleteFalse(Pageable pageable);
}
