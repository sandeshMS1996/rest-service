package com.healthcare.restservice.repos;


import com.healthcare.restservice.models.Product;
import com.healthcare.restservice.models.ProductCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

    List<Product> findProductByCategoryID(Long categoryId);

    List<Product> findProductByProductCompany(ProductCompany productCompany);

    Product findProductByName(String name);
}
