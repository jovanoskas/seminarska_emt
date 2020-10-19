package com.example.seminarska_emt.Service;

import com.example.seminarska_emt.model.ShoppingCart;
import com.example.seminarska_emt.model.dto.ChargeRequest;

import java.util.List;

public interface ShoppingCartService {
    List<ShoppingCart> findAllByUsername(String userId);
    ShoppingCart createNewShoppingCart(String userId);
    ShoppingCart addSongToShoppingCart(String userId, String productId);
    ShoppingCart removeSongFromShoppingCart(String userId, Long productId);
    ShoppingCart getActiveShoppingCart(String userId);
    ShoppingCart cancelActiveShoppingCart(String userId);
    ShoppingCart checkoutShoppingCart(String userId, ChargeRequest chargeRequest);
    ShoppingCart findActiveShoppingCartByUsername(String userId);
}
