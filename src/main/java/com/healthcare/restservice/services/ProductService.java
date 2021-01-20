package com.healthcare.restservice.services;

import com.healthcare.restservice.models.Category;
import com.healthcare.restservice.models.Product;
import com.healthcare.restservice.models.ProductCompany;
import com.healthcare.restservice.models.Purchase;
import com.healthcare.restservice.repos.ProductRepo;
import com.healthcare.restservice.utils.DiscountProcesser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private DiscountProcesser discountProcesser;

    public Product addNewProduct(Product product) {
        return this.productRepo.save(product);
    }

    public void deleteProductById(Long id) {
        this.productRepo.deleteById(id);
    }

    public List<Product> getAllProducts() {
        return this.discountProcesser.updateOptimalDiscount(this.productRepo.findAll());
    }

    public Optional<Product> getProductById(Long id) {
        return this.discountProcesser.updateOptimalDiscount(this.productRepo.findById(id));
    }
    
    public List<Product> filterProductsByCategory(Long categoryId) {
        return this.discountProcesser.updateOptimalDiscount(this.productRepo.findProductByCategoryID(categoryId));
    }

    public List<Product> filterProductsByCompany(ProductCompany companies) {
        return this.discountProcesser.updateOptimalDiscount(this.productRepo.findProductByProductCompany(companies));
    }

    public Double getCostByProductId(Long id) {
        Optional<Product> byId = this.productRepo.findById(id);
        if(byId.isPresent()) {
            return byId.get().getPrice();
        }
        return null;
    }

    public Boolean checkIfExist(List<Purchase> purchaseList) {
        return purchaseList.stream()
                .map(purchase -> new Product(purchase.getProduct().getId()))
                .allMatch(a -> this.productRepo.existsById(a.getId()));
    }


}
