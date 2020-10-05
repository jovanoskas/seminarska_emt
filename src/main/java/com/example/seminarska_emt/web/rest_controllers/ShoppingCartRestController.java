package com.example.seminarska_emt.web.rest_controllers;

import com.example.seminarska_emt.Service.ArtistService;
import com.example.seminarska_emt.Service.ShoppingCartService;
import com.example.seminarska_emt.model.ShoppingCart;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-carts")
public class ShoppingCartRestController {

    private final ShoppingCartService shoppingCartService;
    private final ArtistService artistService;

    public ShoppingCartRestController(ShoppingCartService shoppingCartService,
                                      ArtistService artistService) {
        this.shoppingCartService = shoppingCartService;
        this.artistService = artistService;
    }

    @GetMapping
    public List<ShoppingCart> findAllByUsername() {
        return this.shoppingCartService.findAllByUsername(this.artistService.getCurrentUserId());
    }

    @PostMapping
    public ShoppingCart createNewShoppingCart() {
        return this.shoppingCartService.createNewShoppingCart(this.artistService.getCurrentUserId());
    }

    @PatchMapping("/{songId}/songs")
    public ShoppingCart addProductToCart(@PathVariable Long productId) {
        return this.shoppingCartService.addSongToShoppingCart(
                this.artistService.getCurrentUserId(),
                productId);
    }

    @DeleteMapping("/{songId}/songs")
    public ShoppingCart removeSongFromShoppingCart(@PathVariable Long productId) {
        return this.shoppingCartService.removeSongFromShoppingCart(
                this.artistService.getCurrentUserId(),
                productId
        );
    }

    @PatchMapping("/cancel")
    public ShoppingCart cancelActiveShoppingCart() {
        return this.shoppingCartService.cancelActiveShoppingCart(this.artistService.getCurrentUserId());
    }

    @PatchMapping("/checkout")
    public ShoppingCart checkoutActiveShoppingCart() {
        return null;
    }



}
