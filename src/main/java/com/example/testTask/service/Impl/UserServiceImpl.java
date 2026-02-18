package com.example.testTask.service.Impl;

import com.example.testTask.dto.*;
import com.example.testTask.enums.Role;
import com.example.testTask.models.User;
import com.example.testTask.repository.UserRepository;
import com.example.testTask.security.jwt.JWTService;
import com.example.testTask.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JWTService jwtService;

    @Override
    public JwtAuthenticationDto signIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException{
        User user = findByCredentials(userCredentialsDto);
        return jwtService.generateAuthToken(user.getUsername());
    }

    @Override
    public JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception{
        String refreshToken = refreshTokenDto.getRefreshToken();
        if (refreshToken != null && jwtService.validateJwtToken(refreshToken)) {
            User user = findByUsername(jwtService.getUsernameFromToken(refreshToken));
            return jwtService.refreshBaseToken(user.getUsername(), refreshToken);
        }
        throw new AuthenticationException("Invalid refresh token");
    }

    public UserDto createUser(UserCreateDto userDto) {

        if (userDto.username() == null || userDto.username().isBlank()) {
            throw new IllegalArgumentException("Username cannot be blank");
        }

        if (userRepository.existsByUsername(userDto.username())) {
            throw new IllegalStateException("Username already exists");
        }

        User user = new User();
        user.setUsername(userDto.username());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setName(userDto.name());
        user.setEnabled(true);
        user.getRole().add(Role.USER);

        User savedUser = userRepository.save(user);
        log.info("Saved user: {}", savedUser);

        return new UserDto(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getName(),
                savedUser.getRole()
        );

    }

    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        return mapToUserDto(user);
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToUserDto)
                .orElse(null);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().
                stream()
                .map(this::mapToUserDto)
                .toList();
    }

    private UserDto mapToUserDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getName(), user.getRole());
    }

    private User findByCredentials(UserCredentialsDto userCredentialsDto) {
        Optional<User> optionalUser = userRepository.findByUsername(userCredentialsDto.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(userCredentialsDto.getPassword(), user.getPassword())) {
                return user;
            }
        }
        throw new ArithmeticException("Username or password is not correct");
    }

    private User findByUsername(String username) throws Exception {
        return userRepository.findByUsername(username).orElseThrow(() -> new Exception(String.format("Username %s not found", username)));
    }
}
