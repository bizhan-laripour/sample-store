package org.egs.sampleStore.dao;

import org.egs.sampleStore.entity.Category;
import org.egs.sampleStore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findProductByProductName(String productName);

    List<Product> findProductsByProductNameContains(String SubProductName);

    List<Product> findProductsByCategory_CategoryName(String name);

    List<Product> findProductsByAverageRate(double rate);

    List<Product> findProductsByAverageRateIsGreaterThanEqual(double rate);

    List<Product> findProductsByAverageRateIsLessThanEqual(double rate);

    List<Product> findProductsByAverageRateIsBetween(double less , double more);
    Product findByProductId(Integer productId);
}
