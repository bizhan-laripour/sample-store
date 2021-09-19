package org.egs.sampleStore.service;

import org.egs.sampleStore.dao.ProductRepository;
import org.egs.sampleStore.dto.CategoryDto;
import org.egs.sampleStore.dto.ProductDto;
import org.egs.sampleStore.entity.Category;
import org.egs.sampleStore.entity.Comment;
import org.egs.sampleStore.entity.Product;
import org.egs.sampleStore.exception.CustomException;
import org.egs.sampleStore.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    private ProductMapper productMapper;

    private CategoryService categoryService;

    @Autowired
    public ProductService(ProductRepository productRepository , ProductMapper productMapper, CategoryService categoryService){
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryService = categoryService;
    }

    @Transactional
    public ProductDto save(ProductDto product){
        CategoryDto category = categoryService.findCategoryByCategoryName(product.getCategory().getCategoryName());
        if(category != null) {
            product.setCategory(category);
            productRepository.save(productMapper.dtoToEntity(product));
            return product;
        }
        throw new CustomException("there is no category with this category name" , HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public List<ProductDto> saveAll(List<ProductDto> products){
         productRepository.saveAll(productMapper.dtosToEntities(products));
         return products;
    }

    @Transactional
    public void delete(ProductDto product){
        Product product1 = productRepository.findByProductId(product.getProductId());
        productRepository.delete(product1);
    }


    public ProductDto findProductByProductName(String name){
        Product product = productRepository.findProductByProductName(name);
        if(product != null){
            return productMapper.entityToDto(product);
        }
        return null;
    }

    public List<ProductDto> findProductsByProductNameContains(String subProductName){
        List<Product> product =  productRepository.findProductsByProductNameContains(subProductName);
        if(product != null && product.size() != 0){
            return productMapper.entitiesToDtos(product);
        }
        return null;
    }

    public List<ProductDto> findProductsByCategoryName(String categoryName){
        List<Product> products = productRepository.findProductsByCategory_CategoryName(categoryName);
        if(products != null && products.size() != 0){
            return productMapper.entitiesToDtos(products);
        }
        return null;
    }


    public List<ProductDto> findProductsByRate(double exactRate){
        List<Product> products = productRepository.findProductsByAverageRate(exactRate);
        if(products != null && products.size() != 0){
            return productMapper.entitiesToDtos(products);
        }
        return null;
    }


    public List<ProductDto> findProductsByRateIsGreaterThanEqual(double rate){
        List<Product> products = productRepository.findProductsByAverageRateIsGreaterThanEqual(rate);
        if(products != null && products.size() != 0){
            return productMapper.entitiesToDtos(products);
        }
        return null;
    }


    public List<ProductDto> findProductsByRateIsLessThanEqual(double rate){
        List<Product> products = productRepository.findProductsByAverageRateIsLessThanEqual(rate);
        if(products != null && products.size() != 0){
            return productMapper.entitiesToDtos(products);
        }
        return null;
    }


    public List<ProductDto> findProductsByRateIsBetween(double less , double more){
        List<Product> products = productRepository.findProductsByAverageRateIsBetween(less , more);
        if(products != null && products.size() != 0){
            return productMapper.entitiesToDtos(products);
        }
        return null;
    }

    public ProductDto rateProduct(Comment comment){
        return null;
    }

}
