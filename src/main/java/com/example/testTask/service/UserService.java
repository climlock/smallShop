package com.example.testTask.service;

import com.example.testTask.dto.*;
import com.example.testTask.models.User;

import javax.naming.AuthenticationException;
import java.util.List;


public interface UserService {
    JwtAuthenticationDto signIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException;
    JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception;

    UserDto createUser(UserCreateDto userDto);
    UserDto getUserByUsername(String username);
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();


}
