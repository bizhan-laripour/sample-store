package org.egs.sampleStore.controller;

import org.egs.sampleStore.dto.CategoryDto;
import org.egs.sampleStore.enums.Role;
import org.egs.sampleStore.exception.CustomException;
import org.egs.sampleStore.security.AutherizationHeader;
import org.egs.sampleStore.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    private CategoryService categoryService;

    private AutherizationHeader autherizationHeader;

    public CategoryController(CategoryService categoryService , AutherizationHeader autherizationHeader){
        this.categoryService = categoryService;
        this.autherizationHeader = autherizationHeader;
    }

    @RequestMapping(method = RequestMethod.POST , path = "/api/category/add_category")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        String role = autherizationHeader.getRoleFromToken();
        if(role.equals(Role.ROLE_ADMIN.getAuthority())){
            return ResponseEntity.ok(categoryService.save(categoryDto));
        }
        throw new CustomException("you have not permission to add category" , HttpStatus.FORBIDDEN);
    }

    @RequestMapping(method = RequestMethod.POST , path = "/api/category/delete_category")
    public ResponseEntity<CategoryDto> deleteCategory(@RequestBody CategoryDto categoryDto){
        String role = autherizationHeader.getRoleFromToken();
        if(role.equals(Role.ROLE_ADMIN.getAuthority())){
            categoryService.delete(categoryDto);
            return ResponseEntity.ok(categoryDto);
        }
        throw new CustomException("you have not permission to delete category" , HttpStatus.FORBIDDEN);
    }


    @RequestMapping(method = RequestMethod.POST , path = "/api/category/fetch_all_categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        return ResponseEntity.ok(categoryService.findAll());
    }

    @RequestMapping(method = RequestMethod.POST , path = "/api/category/fetch_categories_by_names_contain")
    public ResponseEntity<List<CategoryDto>> getCategoriesByNamesContain(@RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.findCategoriesByCategoryNameContains(categoryDto.getCategoryName()));
    }
}
