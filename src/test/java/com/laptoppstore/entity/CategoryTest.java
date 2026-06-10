package com.laptoppstore.entity;

import com.laptoppstore.repository.CategoryRepository;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CategoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @AfterEach
    void tearDown() {
        categoryRepository.deleteAll();
    }

    @Test
    void shouldSaveCategorySuccessfully() {
        Category cat = new Category();
        cat.setName("Laptop Gaming");
        cat.setSlug("laptop-gaming");

        Category saved = categoryRepository.save(cat);

        assertNotNull(saved.getId());
        assertEquals("Laptop Gaming", saved.getName());
    }

    @Test
    void shouldFailWhenNameIsNull() {
        Category cat = new Category();
        cat.setSlug("laptop-gaming");

        assertThrows(Exception.class, () -> categoryRepository.save(cat));
    }

    @Test
    void shouldFailWhenSlugIsDuplicate() {
        Category cat1 = new Category();
        cat1.setName("Laptop Gaming");
        cat1.setSlug("laptop-gaming");
        categoryRepository.save(cat1);

        Category cat2 = new Category();
        cat2.setName("Laptop Gaming 2");
        cat2.setSlug("laptop-gaming"); // slug trùng
        assertThrows(Exception.class, () -> categoryRepository.save(cat2));
    }
}
