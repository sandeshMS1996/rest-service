package com.healthcare.restservice.repos;


import com.healthcare.restservice.models.Category;
import com.healthcare.restservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

}

