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
        List<Purchase> collect = purchaseList.stream()
                .map(a -> a.updateProduct(this.productService.
                        getProductById(a.getProduct().getId()).orElse(null)))
                .collect(Collectors.toList());
        if(!collect.stream().allMatch(purchase -> purchase.getProduct() != null)) {
            return false;
        }
        Double count = productService.getTotalPrice(collect);
        System.out.println("count " + count);
        System.out.println(count + " ==> " + paymentMade + " ==> " + (paymentMade < count));
        if(count == 0  || (paymentMade < count))
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
                this.purchaseRepo.save(purchase);
            } else {
                return false;
            }
        }
        return true;
    }
}
