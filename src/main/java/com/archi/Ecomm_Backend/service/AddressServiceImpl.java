package com.archi.Ecomm_Backend.service;

import com.archi.Ecomm_Backend.exceptions.APIException;
import com.archi.Ecomm_Backend.exceptions.ResourceNotFoundException;
import com.archi.Ecomm_Backend.models.Address;
import com.archi.Ecomm_Backend.models.User;
import com.archi.Ecomm_Backend.payloads.AddressDTO;
import com.archi.Ecomm_Backend.repository.AddressRepo;
import com.archi.Ecomm_Backend.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor

public class AddressServiceImpl implements AddressService{

    private final AddressRepo addressRepo;

    private final UserRepo userRepo;

    private final ModelMapper modelMapper;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {
        String buildingName = addressDTO.getBuildingName();
        String street = addressDTO.getStreet();
        String city = addressDTO.getState();
        String state = addressDTO.getState();
        String country = addressDTO.getCountry();
        String pincode = addressDTO.getPincode();

        Address existingAddresses = addressRepo.findByBuildingNameAndStreetAndCityAndStateAndCountryAndPincode(buildingName,street,city,state,country,pincode);

        if (existingAddresses != null){
            throw new APIException("Address already exists with addressId " + existingAddresses.getAddressId());
        }

        Address address = modelMapper.map(addressDTO, Address.class);

        Address savedAddress= addressRepo.save(address);

        return modelMapper.map(savedAddress, AddressDTO.class);

    }

    @Override
    public List<AddressDTO> getAddress() {
        List<Address> addresses= addressRepo.findAll();

        List<AddressDTO> addressDTOS = addresses.stream().map(address -> modelMapper.map(address, AddressDTO.class))
                .collect(Collectors.toList());

        return addressDTOS;
    }

    @Override
    public AddressDTO getAddress(Long addressId) {
        Address address= addressRepo.findById(addressId).orElseThrow(()-> new ResourceNotFoundException("Address", "addressId", addressId));

        return modelMapper.map(address, AddressDTO.class);

    }

    @Override
    public AddressDTO updateAddress(AddressDTO addressDTO, Long addressId) {
        Address existingAddress = addressRepo.findByBuildingNameAndStreetAndCityAndStateAndCountryAndPincode(
                addressDTO.getBuildingName(), addressDTO.getStreet(), addressDTO.getCity(), addressDTO.getState(),
                addressDTO.getCountry(), addressDTO.getPincode());

        if(existingAddress == null){
            existingAddress = addressRepo.findById(addressId).orElseThrow(()-> new ResourceNotFoundException("Address", "addressId", addressId));

            existingAddress.setBuildingName(addressDTO.getBuildingName());
            existingAddress.setStreet(addressDTO.getStreet());
            existingAddress.setCity(addressDTO.getCity());
            existingAddress.setState(addressDTO.getState());
            existingAddress.setCountry(addressDTO.getCountry());
            existingAddress.setPincode(addressDTO.getPincode());

            Address updatedAddress = addressRepo.save(existingAddress);

            return modelMapper.map(updatedAddress, AddressDTO.class);

        } else {
            List<User> users = userRepo.findByAddress(addressId);
            final Address a = existingAddress;

            users.forEach(user -> user.getAddresses().add(a));

            deleteAddress(addressId);

            return modelMapper.map(existingAddress, AddressDTO.class);

        }
    }

    @Override
    public String deleteAddress(Long addressId) {
        Address existingAddress = addressRepo.findById(addressId)
                .orElseThrow(()-> new ResourceNotFoundException("Address", "addressId", addressId));

        List<User> users = userRepo.findByAddress(addressId);

        users.forEach(user -> {
            user.getAddresses().remove(existingAddress);

            userRepo.save(user);
        });

        addressRepo.deleteById(addressId);

        return "Address deleted successfully with addressId " + addressId;
    }
}
