package com.healthcare.restservice.services;

import com.healthcare.restservice.models.Category;
import com.healthcare.restservice.repos.CategoryRepo;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public Category addNewCategory(Category category) {
        return this.categoryRepo.save(category);
    }

    public List<Category> getAllCategories() {
        return this.categoryRepo.findAll();
    }

    public Category getCategoryById(Long id) {
        return this.categoryRepo.findById(id).orElse(null);
    }
}
