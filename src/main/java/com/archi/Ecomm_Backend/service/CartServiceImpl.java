package com.archi.Ecomm_Backend.service;

import com.archi.Ecomm_Backend.payloads.CartDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Override
    public CartDTO addProductToCart(Long cartId, Long productId, Integer quantity) {
        return null;
    }

    @Override
    public List<CartDTO> getAllCarts() {
        return List.of();
    }

    @Override
    public CartDTO getCart(Long customerId, Long cartId) {
        return null;
    }

    @Override
    public CartDTO updateProductQuantityInCart(Long cartId, Long productId, Integer quantity) {
        return null;
    }

    @Override
    public CartDTO deleteProductFromCart(Long cartId, Long productId) {
        return null;
    }
}
