package com.archi.Ecomm_Backend.service;

import com.archi.Ecomm_Backend.exceptions.APIException;
import com.archi.Ecomm_Backend.models.*;
import com.archi.Ecomm_Backend.payloads.AddressDTO;
import com.archi.Ecomm_Backend.payloads.UserDTO;
import com.archi.Ecomm_Backend.payloads.UserResponse;
import com.archi.Ecomm_Backend.repository.AddressRepo;
import com.archi.Ecomm_Backend.repository.RoleRepo;
import com.archi.Ecomm_Backend.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;


@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;

    private final RoleRepo roleRepo;

    private final AddressRepo addressRepo;

    private final CartService cartService;

    private final ModelMapper modelMapper;

    @Override
    public UserDTO registerUser(UserDTO userDTO) {

        try {
            User user = modelMapper.map(userDTO, User.class);

            Cart cart = new Cart();
            user.setCart(cart);

            Role customerRole = roleRepo.findByRoleType(RoleType.ROLE_CUSTOMER);
            if(customerRole == null){
                throw new RuntimeException("Default Role CUSTOMER Not Found!");
            }
            user.getRoles().add(customerRole);

            AddressDTO addressDTO = userDTO.getAddress();
            Address address= addressRepo.findByBuildingNameAndStreetAndCityAndStateAndCountryAndPincode(
                    addressDTO.getBuildingName(),
                    addressDTO.getStreet(),
                    addressDTO.getCity(),
                    addressDTO.getState(),
                    addressDTO.getCountry(),
                    addressDTO.getPincode()
            ).orElseGet(()-> createNewAddress(addressDTO));

            user.setAddresses(List.of(address));

            User registeredUser = userRepo.save(user);

            cart.setUser(registeredUser);

            userDTO = modelMapper.map(registeredUser, UserDTO.class);

            userDTO.setAddress(modelMapper.map(user.getAddresses().stream().findFirst().get(), AddressDTO.class));

             return userDTO;
        } catch (DataIntegrityViolationException e) {
            throw new APIException("User already exists with emailId: " + userDTO.getEmail());
        }

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

    private Address createNewAddress(AddressDTO addressDTO) {
        Address newAddress = modelMapper.map(addressDTO, Address.class);
        return addressRepo.save(newAddress);
    }

}
