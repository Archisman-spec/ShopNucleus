package com.archi.Ecomm_Backend.service;

import com.archi.Ecomm_Backend.payloads.AddressDTO;
import com.archi.Ecomm_Backend.repository.AddressRepo;

import java.util.List;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO);

    List<AddressDTO> getAddress();

    AddressDTO getAddress(Long addressId);

    AddressDTO updateAddress(AddressDTO addressDTO, Long addressId);

    String deleteAddress(Long addressId);

}
