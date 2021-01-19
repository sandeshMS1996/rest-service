package com.healthcare.restservice.controller;


import com.healthcare.restservice.models.Category;
import com.healthcare.restservice.models.Product;
import com.healthcare.restservice.models.ProductCompany;
import com.healthcare.restservice.services.CategoryService;
import com.healthcare.restservice.services.ProductCompanyService;
import com.healthcare.restservice.services.ProductService;
import com.healthcare.restservice.utils.FileStorageService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductCompanyService productCompanyService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping(value = "add-new-product", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Product> addNewProduct(@RequestPart("data") String product1,
                                                 @RequestPart("file") MultipartFile multipartFile) throws Exception {
        /*String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
*/

        // System.out.println(fileName);
        Product product = new ObjectMapper().readValue(product1, Product.class);
        Product newProduct = this.productService.addNewProduct(product);
        if (newProduct == null) {
            return ResponseEntity.badRequest().body(null);
        }
        String fileName = fileStorageService.storeFile(multipartFile, newProduct.getId());
        return ResponseEntity.ok(newProduct);
    }

    @PostMapping("add-new-category")
    public ResponseEntity<Category> addNewCategory(@RequestBody Category category) {
        Category newProduct = this.categoryService.addNewCategory(category);
        System.out.println(category);
        if (newProduct == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(newProduct);
    }

    @PostMapping(value = "add-new-company", consumes = {})
    public ResponseEntity<ProductCompany> addNewProductCompany(@RequestBody ProductCompany productCompany) {
        System.out.println(productCompany);
        ProductCompany productCompany1 = this.productCompanyService.addProductCompany(productCompany);
        if (productCompany1 == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(productCompany1);
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<HttpStatus> deleteProductById(@PathVariable("id") String id) {
        this.productService.deleteProductById(Long.valueOf(id));
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @GetMapping("get-all-categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(this.categoryService.getAllCategories());
    }

    @GetMapping("get-all-companies")
    public ResponseEntity<List<ProductCompany>> getAllCompanies() {
        return ResponseEntity.ok(this.productCompanyService.getAllCompanies());
    }

}
