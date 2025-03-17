package com.archi.Ecomm_Backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotBlank
    @Size(min = 6, message = "Product Description must contain atleast 6 characters")
    private String description;

    private Double discount;

    @NotNull
    @DecimalMin(value = "0.00")
    private double price;

    private double specialPrice;

    @NotBlank
    @Size(min = 3, message = "Product Name must contain atleast 3 characters")
    private String productName;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_status")
    private ProductStatus productStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;









}
