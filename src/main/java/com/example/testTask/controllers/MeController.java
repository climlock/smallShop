package com.example.testTask.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class MeController {

    @GetMapping("/me")
    public Map<String, Object> me(Authentication auth) {
        var roles = auth.getAuthorities().stream()
                .map(a -> a.getAuthority().replace("ROLE_", "")) // ADMIN / USER
                .toList();

        return Map.of(
                "username", auth.getName(),
                "roles", roles
        );
//        return Map.of(
//                "username", auth.getName(),
//                "role", auth.getAuthorities()
//                        .stream()
//                        .map(a -> a.getAuthority())
//                        .toList()
//        );
    }
}
