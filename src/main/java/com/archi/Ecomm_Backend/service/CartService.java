package com.archi.Ecomm_Backend.service;

import com.archi.Ecomm_Backend.payloads.CartDTO;
import org.springframework.stereotype.Component;

import java.util.List;

public interface CartService {
    CartDTO addProductToCart(Long cartId, Long productId, Integer quantity);

    List<CartDTO> getAllCarts();

    CartDTO getCart(Long customerId, Long cartId);

    CartDTO updateProductQuantityInCart(Long cartId, Long productId, Integer quantity);

    CartDTO deleteProductFromCart(Long cartId, Long productId);

}
