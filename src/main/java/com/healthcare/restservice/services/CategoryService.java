package com.healthcare.restservice.services;

import com.healthcare.restservice.models.Category;
import com.healthcare.restservice.repos.CategoryRepo;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public Category addNewCategory(Category category) {
        System.out.println("Server layer");
        return this.categoryRepo.save(category);
    }

    public List<Category> getAllCategories() {
        return this.categoryRepo.findAll();
    }

    public Category getCategoryById(Long id) {
        return this.categoryRepo.findById(id).orElse(null);
    }

    public Category updateCategoryDiscount(long id, int updatedDiscount) {
        Optional<Category> byId = this.categoryRepo.findById(id);
        if(byId.isPresent()) {
            Category category = byId.get();
            category.setDiscount(updatedDiscount);
            return this.categoryRepo.save(category);
        }
        return null;
    }
}
