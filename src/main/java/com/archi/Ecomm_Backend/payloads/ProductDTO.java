package com.archi.Ecomm_Backend.payloads;

import com.archi.Ecomm_Backend.models.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long productId;
    private Long categoryId;
    private String productName;
    private String description;
    private Double discount;
    private Double price;
    private Double specialPrice;
    private Integer quantity;
    private ProductStatus productStatus;


}
