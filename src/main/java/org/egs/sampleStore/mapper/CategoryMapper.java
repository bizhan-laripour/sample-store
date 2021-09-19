package org.egs.sampleStore.mapper;

import org.egs.sampleStore.dto.CategoryDto;
import org.egs.sampleStore.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapper {





    public Category dtoToEntity(CategoryDto categoryDto){
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setCategoryName(categoryDto.getCategoryName());
        return category;
    }

    public CategoryDto entityToDto(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setCategoryName(category.getCategoryName());
        return categoryDto;
    }

    public List<Category> dtosToEntities(List<CategoryDto> categoryDtos){
        List<Category> result = new ArrayList<>();
        for(CategoryDto obj : categoryDtos){
            result.add(dtoToEntity(obj));
        }
        return result;
    }

    public List<CategoryDto> entitiesToDtos(List<Category> categories){
        List<CategoryDto> result = new ArrayList<>();
        for(Category obj : categories){
            result.add(entityToDto(obj));
        }
        return result;
    }
}
