package com.example.testTask.dto;

import com.example.testTask.enums.Role;

import java.util.Set;

public record UserDto(Long id,
                      String username,
                      String name,
                      Set<Role> roles) {
}
