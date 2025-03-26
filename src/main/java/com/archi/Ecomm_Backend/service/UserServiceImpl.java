package com.archi.Ecomm_Backend.service;

import com.archi.Ecomm_Backend.payloads.UserDTO;
import com.archi.Ecomm_Backend.payloads.UserResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService{


    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        return null;
    }

    @Override
    public UserDTO getUsersById(Long userId) {
        return null;
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        return null;
    }

    @Override
    public String deleteUser(Long userId) {
        return "";
    }
}
