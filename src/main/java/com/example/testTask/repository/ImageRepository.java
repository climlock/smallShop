package com.example.testTask.repository;

import com.example.testTask.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ProductImage, Long> {

}
