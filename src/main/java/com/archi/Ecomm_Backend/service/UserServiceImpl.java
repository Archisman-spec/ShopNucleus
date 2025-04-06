package com.archi.Ecomm_Backend.service;

import com.archi.Ecomm_Backend.exceptions.APIException;
import com.archi.Ecomm_Backend.exceptions.ResourceNotFoundException;
import com.archi.Ecomm_Backend.models.*;
import com.archi.Ecomm_Backend.payloads.*;
import com.archi.Ecomm_Backend.repository.AddressRepo;
import com.archi.Ecomm_Backend.repository.RoleRepo;
import com.archi.Ecomm_Backend.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;

    private final RoleRepo roleRepo;

    private final AddressRepo addressRepo;

    private final CartService cartService;

    private final AddressService addressService;

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

            List<Address> addresses = userDTO.getAddress().stream()
                            .map(addressDTO -> {
                                Address existingAddress = addressRepo.findByBuildingNameAndStreetAndCityAndStateAndCountryAndPincode(
                                        addressDTO.getBuildingName(),
                                        addressDTO.getStreet(),
                                        addressDTO.getCity(),
                                        addressDTO.getState(),
                                        addressDTO.getCountry(),
                                        addressDTO.getPincode()
                                );
                                return existingAddress == null ? modelMapper.map(addressService.createAddress(addressDTO), Address.class) : existingAddress;
                            }).collect(Collectors.toList());

            user.setAddresses(addresses);

            User registeredUser = userRepo.save(user);

            cart.setUser(registeredUser);

            userDTO = modelMapper.map(registeredUser, UserDTO.class);

            userDTO.setAddress(registeredUser.getAddresses().stream()
                    .map(address -> modelMapper.map(address, AddressDTO.class))
                    .collect(Collectors.toList()));

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
        User user= userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        List<AddressDTO> addresses = user.getAddresses().stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .collect(Collectors.toList());

        userDTO.setAddress(addresses);

        CartDTO cart= modelMapper.map(user.getCart(), CartDTO.class);

        List<ProductDTO> products = user.getCart().getCartItems().stream().map(item-> modelMapper.map(item.getProduct(), ProductDTO.class)).collect(Collectors.toList());

        userDTO.setCart(cart);

        userDTO.getCart().setProducts(products);

        return userDTO;

    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {

        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setMobileNumber(userDTO.getMobileNumber());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        if (userDTO.getAddress() != null && !userDTO.getAddress().isEmpty()) {
            List<Address> addresses = userDTO.getAddress().stream()
                    .map(addressDTO -> {
                        Address existingAddress = addressRepo.findByBuildingNameAndStreetAndCityAndStateAndCountryAndPincode(
                                addressDTO.getBuildingName(),
                                addressDTO.getStreet(),
                                addressDTO.getCity(),
                                addressDTO.getState(),
                                addressDTO.getCountry(),
                                addressDTO.getPincode()
                        );
                        return existingAddress == null ? modelMapper.map(addressService.createAddress(addressDTO), Address.class) : existingAddress;
                    }).collect(Collectors.toList());

            user.setAddresses(addresses);
        }

        User updatedUser = userRepo.save(user);

        userDTO = modelMapper.map(updatedUser, UserDTO.class);

        List<AddressDTO> addressDTOS= updatedUser.getAddresses().stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .collect(Collectors.toList());

        userDTO.setAddress(addressDTOS);

        CartDTO cart = modelMapper.map(user.getCart(), CartDTO.class);

        List<ProductDTO> products = user.getCart().getCartItems().stream()
                .map(item-> modelMapper.map(item.getProduct(), ProductDTO.class)).collect(Collectors.toList());

        userDTO.setCart(cart);

        userDTO.getCart().setProducts(products);

        return userDTO;

    }


    @Override
    public String deleteUser(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));

        List<CartItem> cartItems = user.getCart().getCartItems();
        Long cartId = user.getCart().getCartId();

        cartItems.forEach(item -> {
            Long productId= item.getProduct().getProductId();
            cartService.deleteProductFromCart(cartId, productId);
        });

        userRepo.delete(user);

        return "User with userId " + userId + " deleted successfully!";

    }
}
