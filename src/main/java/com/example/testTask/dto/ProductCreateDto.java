package com.example.testTask.dto;


public record ProductCreateDto (
        String title,
        String description,
        Double price
) {
}
