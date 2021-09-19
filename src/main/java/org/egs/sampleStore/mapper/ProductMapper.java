package org.egs.sampleStore.mapper;

import org.egs.sampleStore.dto.ProductDto;
import org.egs.sampleStore.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {



    private CategoryMapper categoryMapper;

    @Autowired
    public ProductMapper(CategoryMapper categoryMapper){
        this.categoryMapper = categoryMapper;

    }

    public Product dtoToEntity(ProductDto productDto){

        Product product = new Product();
        product.setId(productDto.getId());
        product.setProductName(productDto.getProductName());
        product.setAverageRate(productDto.getAverageRate());
        product.setPrice(productDto.getPrice());
        product.setProductId(productDto.getProductId());
        product.setCategory(categoryMapper.dtoToEntity(productDto.getCategory()));
        return product;
    }

    public ProductDto entityToDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setProductName(product.getProductName());
        productDto.setPrice(product.getPrice());
        productDto.setAverageRate(product.getAverageRate());
        productDto.setProductId(product.getProductId());
        productDto.setCategory(categoryMapper.entityToDto(product.getCategory()));
        return productDto;
    }

    public List<ProductDto> entitiesToDtos(List<Product> products){
        List<ProductDto> result = new ArrayList<>();
        for(Product obj : products){
            result.add(entityToDto(obj));
        }
        return result;
    }

    public List<Product> dtosToEntities(List<ProductDto> productDtos){
        List<Product> result = new ArrayList<>();
        for(ProductDto obj : productDtos){
            result.add(dtoToEntity(obj));
        }
        return result;
    }
}
