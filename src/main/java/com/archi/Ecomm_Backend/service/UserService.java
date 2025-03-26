package com.archi.Ecomm_Backend.service;

import com.archi.Ecomm_Backend.payloads.UserDTO;
import com.archi.Ecomm_Backend.payloads.UserResponse;


public interface UserService {
    UserDTO registerUser(UserDTO userDTO);

    UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    UserDTO getUsersById(Long userId);

    UserDTO updateUser(Long userId, UserDTO userDTO);

    String deleteUser(Long userId);

}
