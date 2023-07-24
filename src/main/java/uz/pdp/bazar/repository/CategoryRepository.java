package uz.pdp.bazar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.bazar.entity.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    boolean existsByNameAndParentCategoryIdAndActiveTrue(String name, Integer parentCategoryId);

    List<Category> findAllByParentCategoryIdAndActiveTrue(Integer parentCategoryId);

    List<Category> findAllByParentCategoryAndActiveTrue(Category parentCategory);
}
