package com.example.testTask.controllers;

import com.example.testTask.dto.ProductCreateDto;
import com.example.testTask.dto.ProductDto;
import com.example.testTask.models.Product;
import com.example.testTask.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductApiController {

    private final ProductService productService;

    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductDto create(
            @RequestParam String title,
            @RequestParam(required = false) String description,
            @RequestParam Double price,
            @RequestParam("images") List<MultipartFile> images,
            @RequestParam(defaultValue = "0") int previewIndex
    ) {
        return productService.createProduct(new ProductCreateDto(title, description, price), images, previewIndex);
    }
}
