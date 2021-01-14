package com.healthcare.restservice.services;


import com.healthcare.restservice.models.Product;
import com.healthcare.restservice.models.Purchase;
import com.healthcare.restservice.repos.ProductRepo;
import com.healthcare.restservice.repos.PurchaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepo purchaseRepo;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepo productRepo;

    @Transactional
    public Boolean addPurchaseRecord(List<Purchase> purchaseList, Double paymentMade) {
        Double count = purchaseList.stream()
                .map(a -> productService.getCostByProductId(a.getProduct().getId()) * a.getCount()).reduce(Double::sum).get();
        System.out.println("count " + count);
        if(!paymentMade.equals(count) && this.productService.checkIfExist(purchaseList))
            return false;
        for (Purchase purchase : purchaseList) {
            Product product = this.productService.getProductById(purchase.getProduct().getId()).get();
            int updatedStock = product.getStock() - purchase.getCount();
            product.setStock(updatedStock);
            this.productRepo.save(product);
            purchase.setCost(this.productService.getCostByProductId(purchase.getProduct().getId()));
            this.purchaseRepo.save(purchase);
        }
        return true;
    }
}
