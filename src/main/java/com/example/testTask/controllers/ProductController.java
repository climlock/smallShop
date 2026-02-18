package com.example.testTask.controllers;

import com.example.testTask.models.Product;
import com.example.testTask.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProductController {

//    private final ProductService productService;

    @GetMapping("/")
    public String products() {
        return "products";
    }

}
