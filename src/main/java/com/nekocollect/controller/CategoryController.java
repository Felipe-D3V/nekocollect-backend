package com.nekocollect.controller;

import com.nekocollect.model.Category;
import com.nekocollect.repository.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository repository;

    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Category create(@RequestBody Category category) {
        return repository.save(category);
    }

    @GetMapping
    public List<Category> list() {
        return repository.findAll();
    }
}