package com.archi.Ecomm_Backend.repository;

import com.archi.Ecomm_Backend.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface
AddressRepo extends JpaRepository<Address, Long> {
    Address findByBuildingNameAndStreetAndCityAndStateAndCountryAndPincode(String buildingName, String street, String city, String state, String country, String pincode);

}
