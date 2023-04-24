package com.example.springdbrelation.controllers;

import com.example.springdbrelation.models.Category;
import com.example.springdbrelation.repositories.CategoryRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryRepository categoryRepo;

    public CategoryController(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @RequestMapping("/category")
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @RequestMapping("/category/{id}")
    public Category getCategoryById(@PathVariable long id) {
        return categoryRepo.findById(id).isPresent() ? categoryRepo.findById(id).get() : null;
    }

    @RequestMapping("/categoryNew")
    public String addNewCategory(@RequestParam String name) {
        Category category = new Category(name);
        categoryRepo.save(category);
        return "New category added: " + name;
    }

    @RequestMapping("/category/{name}")
    public Category getCategoryByName(@PathVariable String name) {
        return categoryRepo.findByName(name).isPresent() ? categoryRepo.findByName(name).get() : null;
    }
}
