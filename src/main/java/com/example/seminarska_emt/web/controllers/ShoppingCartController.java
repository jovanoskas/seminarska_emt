package com.example.seminarska_emt.web.controllers;

import com.example.seminarska_emt.Service.ArtistService;
import com.example.seminarska_emt.Service.ShoppingCartService;
import com.example.seminarska_emt.model.ShoppingCart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/shopping-carts")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final ArtistService artistService;

    public ShoppingCartController(ShoppingCartService shoppingCartService,
                                  ArtistService artistService) {
        this.shoppingCartService = shoppingCartService;
        this.artistService = artistService;
    }


    @PostMapping("/{songId}/add-song")
    public String addSongToShoppingCart(@PathVariable Long productId) {
        try {
            ShoppingCart shoppingCart = this.shoppingCartService.addSongToShoppingCart(this.artistService.getCurrentUserId(), productId);
        } catch (RuntimeException ex) {
            return "redirect:/songs?error=" + ex.getLocalizedMessage();
        }
        return "redirect:/songs";
    }


    @PostMapping("/{productId}/remove-song")
    public String removeSongToShoppingCart(@PathVariable Long productId) {
        ShoppingCart shoppingCart = this.shoppingCartService.removeSongFromShoppingCart(this.artistService.getCurrentUserId(), productId);
        return "redirect:/songs";
    }
}
