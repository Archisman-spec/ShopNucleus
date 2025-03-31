package com.archi.Ecomm_Backend.repository;

import com.archi.Ecomm_Backend.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
    Cart findByCustomerIdAndCartId(Long customerId, Long cartId);

    List<Cart> findCartsByProductId(Long productId);

}
