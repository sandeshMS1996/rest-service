package com.healthcare.restservice.utils;

import com.healthcare.restservice.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiscountProcesser {
    public Product updateOptimalDiscount(Product product) {
        if(product != null) {
            System.out.println("Discount processing");
            Optional<Double> max = List.of(product.getDiscount(), product.getCategory().getDiscount(),
                    product.getProductCompany().getDiscount())
                    .stream().max(Double::compareTo);
            product.setDiscount(max.get());
            return product;
        }
        return null;
    }

    public List<Product> updateOptimalDiscount(List<Product> product) {
        if(product != null)
            return product.stream().map(this::updateOptimalDiscount).collect(Collectors.toList());
        return null;
    }
    public Optional<Product> updateOptimalDiscount(Optional<Product> product) {
        return product.map(this::updateOptimalDiscount);
    }

}
