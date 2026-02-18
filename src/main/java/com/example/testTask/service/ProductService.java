package com.example.testTask.service;

import com.example.testTask.dto.ProductCreateDto;
import com.example.testTask.dto.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductCreateDto productDto, List<MultipartFile> images, int previewIndex);
    List<ProductDto> getAllProducts();
    ProductDto getProductById(long id);
    void deleteProductById(long id);
}
