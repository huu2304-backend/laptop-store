package com.laptoppstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name="products")
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String name;
    @NotBlank
    @Column(unique = true,nullable = false)
    private String slug;
    private String description;
    @Column(nullable = false,precision = 15,scale = 2)
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    @Min(value = 0, message = "So luong ton kho khong duoc am")
    @Column(nullable = false)
    private int stock =0;
    private String imageUrl;
}
