package org.egs.sampleStore.dao;

import org.egs.sampleStore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category , Integer> {

    Category findCategoryByCategoryName(String name);

    List<Category> findCategoriesByCategoryNameContains(String categoryName);

}
