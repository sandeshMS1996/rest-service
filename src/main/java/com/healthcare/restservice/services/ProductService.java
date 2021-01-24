package com.healthcare.restservice.services;

import com.healthcare.restservice.models.Category;
import com.healthcare.restservice.models.Product;
import com.healthcare.restservice.models.ProductCompany;
import com.healthcare.restservice.models.Purchase;
import com.healthcare.restservice.repos.ProductRepo;
import com.healthcare.restservice.utils.DiscountProcesser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        Optional<Product> product = this.discountProcesser.updateOptimalDiscount(this.productRepo.findById(id));
        System.out.println("returning product");
        return product;
    }
    
    public List<Product> filterProductsByCategory(Long categoryId) {
        return this.discountProcesser.updateOptimalDiscount(this.productRepo.findProductByCategoryID(categoryId));
    }

    public List<Product> filterProductsByCompany(ProductCompany companies) {
        return this.discountProcesser.updateOptimalDiscount(this.productRepo.findProductByProductCompany(companies));
    }

    public Double getCostByProductId(Long id, long count) {
        System.out.println("calculating price");
        Optional<Product> byId = getProductById(id);
        if(byId.isPresent()) {
            Product product = byId.get();
            if(product.getDiscount() > 0)
                return (product.getPrice() - product.getPrice() * (product.getDiscount() / 100)) * count ;
            return product.getPrice();
        }
        return null;
    }
    public Double getTotalPrice(List<Purchase> purchaseList) {
        System.out.println(purchaseList);
        double totalCost = 0.0;
        for (Purchase purchase : purchaseList) {
            Double costByProductId = getCostByProductId(purchase.getProduct().getId(), purchase.getCount());
            System.out.println("done calculatinf price " + purchase.getProduct().getImageName());
            if (costByProductId == null) return null;
            totalCost += costByProductId;
        }
        return totalCost;
    }

    public Product findProductByName(String name) {
        return this.productRepo.findProductByName(name);
    }

    public Product updateProductDiscount(long id, int updatedDiscount) {
        Optional<Product> byId = this.productRepo.findById(id);
        if(byId.isPresent()) {
            Product product = byId.get();
            product.setDiscount(updatedDiscount);
            return this.productRepo.save(product);
        }
        return null;
    }

    public Product updateAvaliabality(Long id, Boolean avalaiablity) {
        Optional<Product> byId = this.productRepo.findById(id);
        if(byId.isPresent()) {
            Product product = byId.get();
            product.setNotDisabled(avalaiablity);
            return this.productRepo.save(product);
        }
        return null;
    }

}
