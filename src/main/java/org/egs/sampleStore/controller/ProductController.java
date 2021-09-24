package org.egs.sampleStore.controller;

import org.egs.sampleStore.dto.ProductDto;
import org.egs.sampleStore.enums.Role;
import org.egs.sampleStore.exception.CustomException;
import org.egs.sampleStore.security.AutherizationHeader;
import org.egs.sampleStore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;

    private AutherizationHeader autherizationHeader;

    @Autowired
    public ProductController(ProductService productService , AutherizationHeader autherizationHeader){
        this.productService = productService;
        this.autherizationHeader = autherizationHeader;
    }

    @RequestMapping(method = RequestMethod.POST , path = "api/product/add_product")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto){
        String role = autherizationHeader.getRoleFromToken();
        if(role.equals(Role.ROLE_ADMIN.getAuthority())){
           return ResponseEntity.ok( productService.save(productDto));
        }
        throw new CustomException("you have not permission to add product" , HttpStatus.FORBIDDEN);
    }

    @RequestMapping(method = RequestMethod.POST , path = "api/product/delete_product")
    public ResponseEntity<ProductDto> deleteProduct(@RequestBody ProductDto productDto){
        String role = autherizationHeader.getRoleFromToken();
        if(role.equals(Role.ROLE_ADMIN.getAuthority())){
            productService.delete(productDto);
            return ResponseEntity.ok(productDto);
        }
        throw new CustomException("you have not permission to add product" , HttpStatus.FORBIDDEN);
    }

    @RequestMapping(method = RequestMethod.POST , path = "api/product/find_products_by_Category_name")
    public ResponseEntity<List<ProductDto>> findProductByCategoryName(@RequestBody ProductDto productDto){
        List<ProductDto> list = productService.findProductsByCategoryName(productDto.getCategory().getCategoryName());
        return ResponseEntity.ok(list);
    }


    @RequestMapping(method = RequestMethod.GET , path = "api/product/find_by_rate_grater")
    public ResponseEntity<List<ProductDto>> findProductsRateGraterThan(@RequestParam double rate){
        return ResponseEntity.ok(productService.findProductsByRateIsGreaterThanEqual(rate));
    }


    @RequestMapping(method = RequestMethod.GET , path = "api/product/find_by_rate_less")
    public ResponseEntity<List<ProductDto>> findProductsRateLessThan(@RequestParam double rate){
        return ResponseEntity.ok(productService.findProductsByRateIsLessThanEqual(rate));
    }


    @RequestMapping(method = RequestMethod.GET , path = "api/product/find_by_rate_les_and_grater")
    public ResponseEntity<List<ProductDto>> findProductsBetweenRates(@RequestParam double less , @RequestParam double more){
        return ResponseEntity.ok(productService.findProductsByRateIsBetween(less, more));
    }
}
