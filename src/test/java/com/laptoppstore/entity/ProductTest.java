package com.laptoppstore.entity;
import jakarta.validation.ConstraintViolationException;
import com.laptoppstore.repository.CategoryRepository;
import com.laptoppstore.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ProductTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void shouldSaveProductSuccessfully() {
        // Bước 1: tạo và lưu Category trước
        Category category = new Category();
        category.setName("Laptop Gaming");
        category.setSlug("laptop-gaming");
        categoryRepository.save(category);

        // Bước 2: tạo Product gắn vào Category đó
        Product product = new Product();
        product.setName("Dell XPS 15");
        product.setSlug("dell-xps-15");
        product.setPrice(new BigDecimal("25000000"));
        product.setCategory(category);  // ← gắn FK vào đây

        Product saved = productRepository.save(product);

        // Bước 3: assert
        assertNotNull(saved.getId());
        assertEquals("Dell XPS 15", saved.getName());
        assertEquals(0, saved.getStock()); // mặc định = 0
    }

    @Test
    void shouldFailWhenNameIsNull() {
        Category category = new Category();
        category.setName("Laptop Gaming");
        category.setSlug("laptop-gaming");
        Category savedCategory = categoryRepository.save(category);

        Product product = new Product();
        // không set name — để null
        product.setSlug("dell-xps-15");
        product.setPrice(new BigDecimal("25000000"));
        product.setCategory(savedCategory);

        assertThrows(ConstraintViolationException.class, () -> productRepository.save(product));
    }
    @Test
    void shouldFailWhenSlugIsDuplicate() {
        Category category = new Category();
        category.setName("Laptop Gaming");
        category.setSlug("laptop-gaming");
        Category savedCategory = categoryRepository.save(category); // ← dùng object đã save

        Product product1 = new Product();
        product1.setName("Dell XPS 15");
        product1.setSlug("dell-xps-15");
        product1.setPrice(new BigDecimal("25000000"));
        product1.setCategory(savedCategory); // ← dùng savedCategory
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Dell XPS 16");
        product2.setSlug("dell-xps-15"); // trùng slug
        product2.setPrice(new BigDecimal("30000000"));
        product2.setCategory(savedCategory); // ← dùng savedCategory

        assertThrows(Exception.class, () -> productRepository.save(product2));
    }
}
