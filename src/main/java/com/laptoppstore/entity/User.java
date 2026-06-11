package com.laptoppstore.entity;

import com.laptoppstore.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "tên khách hàng không được để trống")
    private String fullName;
    @Email(message = "email khong dung dinh dang")
    @NotBlank(message = "email khong duoc de trong")
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    private String phone;
    @NotBlank
    private String address;
    private boolean enable = true;
    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_CUSTOMER;
}
