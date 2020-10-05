package com.example.seminarska_emt.model.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class ShoppingCartIsAlreadyCreated extends RuntimeException {

    public ShoppingCartIsAlreadyCreated(String userId) {
        super("Shopping cart is already created!");
    }
}
