package com.example.testTask.dto;


import java.util.List;

public record ProductDto (
        Long id,
        String title,
        String description,
        Double price,
        Long previewImageId,
        List<Long> imagesId
){
}
