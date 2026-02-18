package com.example.testTask.dto;


public record ProductDto (
        Long id,
        String title,
        String description,
        Double price,
        Long previewImageId
){
}
