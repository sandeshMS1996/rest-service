package com.healthcare.restservice.controller;

import com.healthcare.restservice.models.Product;
import com.healthcare.restservice.models.Purchase;
import com.healthcare.restservice.services.ProductService;
import com.healthcare.restservice.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("get-products-by-category/{cat-id}")
    public ResponseEntity<List<Product>> findProductsByCategory(@PathVariable("cat-id") Long categoryId) {
        List<Product> products = this.productService.filterProductsByCategory(categoryId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(products);
    }

    @GetMapping("get-products-by-company/{com-id}")
    public ResponseEntity<List<Product>> findProductByCompany(@PathVariable("com-id") Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
          this.productService.filterProductsByCompany(id));
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
}
