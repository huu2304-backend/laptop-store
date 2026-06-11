package com.laptoppstore.controller;

import com.laptoppstore.service.CategoryService;
import com.laptoppstore.service.OrderService;
import com.laptoppstore.service.ProductService;
import com.laptoppstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminDashboardController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalCategories", categoryService.findAll().size());
        model.addAttribute("totalProducts", productService.findAll().size());
        model.addAttribute("totalOrders", orderService.findAll().size());
        model.addAttribute("totalUsers", userService.findAll().size());
        return "admin/dashboard";
    }
}
