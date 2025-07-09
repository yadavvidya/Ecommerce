package com.ecommerce.user_service.service;

import java.util.List;

import com.ecommerce.user_service.dto.CreateUserRequest;
import com.ecommerce.user_service.dto.UserDTO;

public interface UserService {
    UserDTO createUser(CreateUserRequest request);
    UserDTO updateUser(Long id, CreateUserRequest request);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    void deleteUser(Long id);
}
