package com.laptoppstore.service;

import com.laptoppstore.entity.Category;
import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(Long id);
    void save(Category category);
    void deleteById(Long id);
}
