package com.example.seminarska_emt.Service;

import com.example.seminarska_emt.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    List<ShoppingCart> findAllByUsername(String userId);
    ShoppingCart createNewShoppingCart(String userId);
    ShoppingCart addSongToShoppingCart(String userId, Long productId);
    ShoppingCart removeSongFromShoppingCart(String userId, Long productId);
    ShoppingCart getActiveShoppingCart(String userId);
    ShoppingCart cancelActiveShoppingCart(String userId);
}
