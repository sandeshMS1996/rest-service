package com.healthcare.restservice.controller;

import com.healthcare.restservice.models.Category;
import com.healthcare.restservice.models.Product;
import com.healthcare.restservice.models.ProductCompany;
import com.healthcare.restservice.models.Purchase;
import com.healthcare.restservice.services.CategoryService;
import com.healthcare.restservice.services.ProductService;
import com.healthcare.restservice.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/customer")
@CrossOrigin("*")
public class CustomerController {
    
    @Autowired
    private ProductService productService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("all-products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> allProducts = this.productService.getAllProducts();
        return ResponseEntity.ok(allProducts);
    }

    @GetMapping("product/{id}")
    public ResponseEntity<Optional<Product>> getProductByID(@PathVariable("id") String id) {
        Optional<Product> byId = this.productService.getProductById(Long.valueOf(id));
        return ResponseEntity.ok(byId);
    }


    @PostMapping("purchase")
    public ResponseEntity<Boolean> purchase(@RequestBody List<Purchase> purchaseList,
                                            @RequestParam("payment-made") Double paymentMade) {
        System.out.println(purchaseList + "\n" + paymentMade);
        Boolean record = this.purchaseService.addPurchaseRecord(purchaseList, paymentMade);
        if(record)
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(false);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(true);
    }

    @PostMapping("filter")
    public ResponseEntity<List<Product>> filter(@RequestParam(required = false, value = "companyId") Long companyID,
                                          @RequestParam(required = false, value = "categoryId") Long categoryId) {
        System.out.println(companyID + " " + categoryId);
        if(companyID  == null && categoryId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else if(companyID != null && categoryId == null) {
            System.out.println("company ID " + companyID);
            List<Product> products = this.productService.filterProductsByCompany(new ProductCompany(companyID));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(products);
        } else if(companyID == null) {
            System.out.println("category ID " + categoryId);
            List<Product> products = this.productService.filterProductsByCategory(categoryId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(products);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(List.of(new Product()));
    }

    @GetMapping("get-category-by-id/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long id) {
        Category category = this.categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }
}
