package com.laptoppstore.controller;

import com.laptoppstore.entity.Product;
import com.laptoppstore.service.ImageService;
import com.laptoppstore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminProductController {
    private final ProductService productService;
    private final ImageService imageService;
    private final com.laptoppstore.service.CategoryService categoryService;

    @GetMapping("/products")
    public String showListProduct(Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin/products/list-products";
    }

    @GetMapping("/products/add")
    public String formAddProducts(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        return "admin/products/add";
    }

    @PostMapping("/products/add")
    public String addProducts(@ModelAttribute Product product,
                              @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        String imageUrl = imageService.saveImage(imageFile);
        if (imageUrl != null) {
            product.setImageUrl(imageUrl);
        }
        productService.save(product);
        return "redirect:/admin/products";
    }
    @GetMapping("/products/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("categories", categoryService.findAll());
        return "admin/products/edit";
    }

    @PostMapping("/products/edit/{id}")
    public String editForm(@PathVariable("id") Long id, @ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/admin/products";
    }

    @PostMapping("/products/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return "redirect:/admin/products";

    }

}
