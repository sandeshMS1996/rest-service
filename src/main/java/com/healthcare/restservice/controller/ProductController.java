package com.healthcare.restservice.controller;


import com.healthcare.restservice.models.Category;
import com.healthcare.restservice.models.Product;
import com.healthcare.restservice.models.ProductCompany;
import com.healthcare.restservice.models.ProductDescription;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class ProductController {

    private final ProductService productService;

    private final CategoryService categoryService;

    private final ProductCompanyService productCompanyService;

    private final FileStorageService fileStorageService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, ProductCompanyService productCompanyService, FileStorageService fileStorageService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.productCompanyService = productCompanyService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping(value = "add-new-product", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Product> addNewProduct(@RequestPart("data") String product1,
                                                 @RequestPart("file") MultipartFile multipartFile) throws Exception {
        Product product = new ObjectMapper().readValue(product1, Product.class);
        Product newProduct = this.productService.addNewProduct(product);
        if (newProduct == null) {
            return ResponseEntity.badRequest().body(null);
        }
        fileStorageService.storeFile(multipartFile, newProduct.getId());
        return ResponseEntity.ok(newProduct);
    }

    @PostMapping("add-new-category")
    public ResponseEntity<Category> addNewCategory(@RequestBody Category category) {
        Category newProduct = this.categoryService.addNewCategory(category);
        System.out.println("controller => " + newProduct);
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

    @GetMapping("get-all-dosageForms")
    public ResponseEntity<List<String>> getDosageForm() {
        List<String> dosage = new ArrayList<>();
        for (ProductDescription.DoseForm value : ProductDescription.DoseForm.values())
            dosage.add(value.name());
        return ResponseEntity.ok(dosage);
    }

    @PostMapping("update-product-discount/{id}")
    public ResponseEntity<Product> updateProductDiscount(@PathVariable("id") Long id,
                                                         @RequestParam("updated-discount") Integer updatedDiscount) {
        Product product = this.productService.updateProductDiscount(id, updatedDiscount);
        return ResponseEntity.ok(product);
    }

    @PostMapping("update-category-discount/{id}")
    public ResponseEntity<Category> updateCategoryDiscount(@PathVariable("id") Long id,
                                                         @RequestParam("updated-discount") Integer updatedDiscount) {
        Category category = this.categoryService.updateCategoryDiscount(id, updatedDiscount);
        return ResponseEntity.ok(category);
    }


    @PostMapping("update-company-discount/{id}")
    public ResponseEntity<ProductCompany> updateCompanyDiscount(@PathVariable("id") Long id,
                                                           @RequestParam("updated-discount") Integer updatedDiscount) {
        ProductCompany discount = this.productCompanyService.updateCompanyDiscount(id, updatedDiscount);
        return ResponseEntity.ok(discount);
    }

    @PostMapping("change-product-availability/{id}")
    public ResponseEntity<Product> updateProductAvailability(@PathVariable("id") long id, @RequestParam("enable") Boolean aBoolean) {
        Product product = this.productService.updateAvaliabality(id, aBoolean);
        return ResponseEntity.ok(product);
    }
}
