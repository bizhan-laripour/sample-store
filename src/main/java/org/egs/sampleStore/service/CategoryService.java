package org.egs.sampleStore.service;

import org.egs.sampleStore.dao.CategoryRepository;
import org.egs.sampleStore.dto.CategoryDto;
import org.egs.sampleStore.entity.Category;
import org.egs.sampleStore.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    private CategoryMapper categoryMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository , CategoryMapper categoryMapper){
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Transactional
    public CategoryDto save(CategoryDto category){
        categoryRepository.save(categoryMapper.dtoToEntity(category));
        return category;

    }

    public List<CategoryDto> findAll(){
        List<Category> categories = categoryRepository.findAll();
        if(categories.size() != 0){
           return categoryMapper.entitiesToDtos(categories);
        }
        return null;
    }

    @Transactional
    public void delete(CategoryDto category){
        Category category1 = categoryRepository.findCategoryByCategoryName(category.getCategoryName());
        categoryRepository.delete(category1);
    }

    public CategoryDto findCategoryByCategoryName(String name){
        Category category = categoryRepository.findCategoryByCategoryName(name);
        if(category != null){
            return categoryMapper.entityToDto(category);
        }
        return null;
    }

    public List<CategoryDto> findCategoriesByCategoryNameContains(String categoryName){
        List<Category> categories = categoryRepository.findCategoriesByCategoryNameContains(categoryName);
        if(categories.size() != 0){
            return categoryMapper.entitiesToDtos(categories);
        }
        return null;
    }



}
