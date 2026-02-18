package com.example.testTask.service.Impl;


import com.example.testTask.dto.ProductCreateDto;
import com.example.testTask.dto.ProductDto;
import com.example.testTask.models.Product;
import com.example.testTask.models.ProductImage;
import com.example.testTask.repository.ImageRepository;
import com.example.testTask.repository.ProductRepository;
import com.example.testTask.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;

    @Override
    public ProductDto createProduct(ProductCreateDto productDto, List<MultipartFile> files, int previewIndex) {

        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("At least one image is required");
        }

        Product product = new Product();
        product.setTitle(productDto.title());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());

        Product savedProduct = productRepository.save(product);
        log.info("Saved user: {}", savedProduct);

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);

            if (file.isEmpty()) {continue;}

            ProductImage img = new ProductImage();
            img.setName(file.getName());
            img.setOriginalPrivateName(file.getOriginalFilename());
            img.setSize(file.getSize());
            img.setContentType(file.getContentType());
            img.setPreviewImage(i == previewIndex);
            img.setProduct(savedProduct);

            try{
                img.setData(file.getBytes());
            } catch (Exception e) {
                throw new IllegalArgumentException("Image data is incorrect");
            }

            imageRepository.save(img);

            if (i == 0) {
                savedProduct.setPreviewImageId(img.getId());
            }
        }

        productRepository.save(savedProduct);

       return mapToDto(savedProduct);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public ProductDto getProductById(long id) {
        return productRepository.findById(id)
                .map(this::mapToDto)
                .orElse(null);
    }

    @Override
    public void deleteProductById(long id) {
        productRepository.deleteById(id);
    }

    private ProductDto mapToDto(Product savedProduct) {
        return new ProductDto(
                savedProduct.getId(),
                savedProduct.getTitle(),
                savedProduct.getDescription(),
                savedProduct.getPrice(),
                savedProduct.getPreviewImageId()
        );
    }
}
