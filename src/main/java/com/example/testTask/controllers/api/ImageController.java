package com.example.testTask.controllers.api;

import com.example.testTask.models.ProductImage;
import com.example.testTask.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageRepository imageRepository;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        ProductImage img = imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));


        return ResponseEntity.ok()
                .header("Content-Type", img.getContentType())
                .body(img.getData());
    }

}
