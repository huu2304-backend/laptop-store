package com.laptoppstore.service;

import com.laptoppstore.entity.Product;
import com.laptoppstore.repository.ProductRepository;
import com.laptoppstore.service.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void shouldReturnAllProducts() {
        // 1. Tạo data giả
        Product p1 = new Product();
        p1.setName("Dell XPS 15");

        Product p2 = new Product();
        p2.setName("Macbook Pro");

        // 2. Chỉ định mock trả về gì khi gọi findAll()
        when(productRepository.findAll())
                .thenReturn(List.of(p1, p2));

        // 3. Gọi service thật
        List<Product> result = productService.findAll();

        // 4. Assert
        assertEquals(2, result.size());
        assertEquals("Dell XPS 15", result.get(0).getName());
    }

    @Test
    void shouldReturnProductWhenFound() {
        Product p1 = new Product();
        p1.setName("Dell XPS 15");
        when(productRepository.findById(1L))
                .thenReturn(Optional.of(p1));
        Product result = productService.findById(1L);
        assertEquals("Dell XPS 15", result.getName());

    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        when(productRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> productService.findById(99L));
    }

}
