package com.healthcare.restservice.services;


import com.healthcare.restservice.models.Product;
import com.healthcare.restservice.models.Purchase;
import com.healthcare.restservice.repos.ProductRepo;
import com.healthcare.restservice.repos.PurchaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepo purchaseRepo;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepo productRepo;

    public List<Purchase> getMyPurchase(String username) {
        return this.purchaseRepo.findAllByUsername(username);
    }

    @Transactional
    public Boolean addPurchaseRecord(List<Purchase> purchaseList, Double paymentMade) {
        /*List<Purchase> collect = purchaseList;*/
        List<Purchase> collect = purchaseList.stream()
                .map(a -> {
                    Purchase purchase = a.updateProduct(this.productService.getProductById(a.getProduct().getId()).orElse(null));
                    /*purchase.getProduct().setCategory(null);*/
                    purchase.getProduct().setProductCompany(null);
                    System.out.println(purchase);
                    return purchase;
                })
                .collect(Collectors.toList());
        /*for (Purchase purchase : purchaseList) {
            Product productById = this.productService.getProductById(purchase.getProduct().getId()).orElse(null);
            purchase.updateProduct(productById);
            collect.add(purchase);
        }*/
        if(!collect.stream().allMatch(purchase -> purchase.getProduct() != null)) {
            return false;
        }
        Double count = paymentMade; /*productService.getTotalPrice(collect);*/
        System.out.println("count " + count);
        System.out.println(count + " ==> " + paymentMade + " ==> " + paymentMade.equals(count));
        if(count == 0  || !paymentMade.equals(count))
            return false;
        for (Purchase purchase : collect) {
            Product product = purchase.getProduct();
            if(product != null) {
                int updatedStock = product.getStock() - purchase.getCount();
                System.out.println(" updated stock " + updatedStock);
                if(updatedStock < 0)
                    return false;
                product.setStock(updatedStock);
                this.productRepo.save(product);
                Double costByProductId = 0.0;
                if(product.getDiscount() > 0)
                    costByProductId = (product.getPrice() - product.getPrice() * (product.getDiscount() / 100)) * count ;
                else costByProductId =  product.getPrice();

                purchase.setCost(costByProductId);
                purchase.setAppliedDiscount(product.getDiscount());
                this.purchaseRepo.save(purchase);
            } else {
                return false;
            }
        }
        return true;
    }
}
