package com.laptoppstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "tên không được để trống")
    @Column(unique = true, nullable = false)
    private String name;
    @NotBlank(message = "slug không được để trống")
    @Column(unique = true, nullable = false)
    private String slug;
    private String description;
}
