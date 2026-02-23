package com.example.testTask.controllers;

import com.example.testTask.dto.ProductDto;
import com.example.testTask.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public String products(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @GetMapping("/product/new")
    public String newProductForm(Model model) {
        return "new_product_form";
    }

    @GetMapping("/product/{id}")
    public String productPage(@PathVariable long id, Model model) {
        ProductDto dto = productService.getProductById(id);
        if (dto == null) {
            model.addAttribute("error", "Product not found");
            return "product_form";
        }
        model.addAttribute("product", dto);
        return "product_form";
    }


}
