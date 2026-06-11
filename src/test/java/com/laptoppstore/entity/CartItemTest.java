package com.laptoppstore.entity;

import com.laptoppstore.enums.Role;
import com.laptoppstore.repository.CartItemRepository;
import com.laptoppstore.repository.CategoryRepository;
import com.laptoppstore.repository.ProductRepository;
import com.laptoppstore.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CartItemTest {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private jakarta.validation.Validator validator;
    @AfterEach
    void tearDown() {
        cartItemRepository.deleteAll();
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldSaveCartItemSuccessfully() {
        // User
        User user = new User();
        user.setFullName("Nguyen Van A");
        user.setEmail("test@gmail.com");
        user.setAddress("Ha Noi");
        user.setRole(Role.ROLE_CUSTOMER);
        User savedUser = userRepository.save(user);

        // Category
        Category category = new Category();
        category.setName("Laptop Gaming");
        category.setSlug("laptop-gaming");
        Category savedCategory = categoryRepository.save(category);

        // Product
        Product product = new Product();
        product.setName("Dell XPS 15");
        product.setSlug("dell-xps-15");
        product.setPrice(new BigDecimal("25000000"));
        product.setCategory(savedCategory);
        Product savedProduct = productRepository.save(product);

        // CartItem
        CartItem cartItem = new CartItem();
        cartItem.setUser(savedUser);
        cartItem.setProduct(savedProduct);
        cartItem.setQuantity(2);

        CartItem saved = cartItemRepository.save(cartItem);

        // Assert
        assertNotNull(saved.getId());
        assertEquals(2, saved.getQuantity());
        assertNotNull(saved.getAddedAt()); // @PrePersist tự set
    }

    @Test
    void shouldFailWhenQuantityIsZero() {

        // User
        User user = new User();
        user.setFullName("Nguyen Van A");
        user.setEmail("test@gmail.com");
        user.setAddress("Ha Noi");
        user.setRole(Role.ROLE_CUSTOMER);
        User savedUser = userRepository.save(user);

        // Category
        Category category = new Category();
        category.setName("Laptop Gaming");
        category.setSlug("laptop-gaming");
        Category savedCategory = categoryRepository.save(category);

        // Product
        Product product = new Product();
        product.setName("Dell XPS 15");
        product.setSlug("dell-xps-15");
        product.setPrice(new BigDecimal("25000000"));
        product.setCategory(savedCategory);
        Product savedProduct = productRepository.save(product);

        // CartItem
        CartItem cartItem = new CartItem();
        cartItem.setUser(savedUser);
        cartItem.setProduct(savedProduct);
        cartItem.setQuantity(0);

        // Assert
        var violations = validator.validate(cartItem);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailWhenUserIsNull() {
        Exception exception = new Exception();

        // Category
        Category category = new Category();
        category.setName("Laptop Gaming");
        category.setSlug("laptop-gaming");
        Category savedCategory = categoryRepository.save(category);

        // Product
        Product product = new Product();
        product.setName("Dell XPS 15");
        product.setSlug("dell-xps-15");
        product.setPrice(new BigDecimal("25000000"));
        product.setCategory(savedCategory);
        Product savedProduct = productRepository.save(product);

        // CartItem
        CartItem cartItem = new CartItem();
        cartItem.setProduct(savedProduct);
        cartItem.setQuantity(2);


        // Assert
        assertThrows(Exception.class,
                () -> cartItemRepository.saveAndFlush(cartItem));
        System.out.println("Exception type: " + exception.getClass().getName());
        System.out.println("Exception message: " + exception.getMessage());

    }

}
