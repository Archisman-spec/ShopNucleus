package com.archi.Ecomm_Backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank
    @Size(min = 5, message = "Building Name must contain atleast 5 characters")
    private String buildingName;

    @NotBlank
    @Size(min = 5, message = "Street Name must contain atleast 5 characters")
    private String street;

    @NotBlank
    @Size(min = 3, message = "City Name must contain atleast 3 characters")
    private String city;

    @NotBlank
    @Size(min = 3, message = "State Name must contain atleast 3 characters")
    private String state;

    @NotBlank
    @Size(min = 3, message = "Country Name must contain atleast 3 characters")
    private String country;

    @NotNull(message = "Pincode cannot be null")
    @Pattern(regexp = "[0-9]{6}", message = "Pincode isnt valid.Must be 6 digits.")
    private String pincode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
