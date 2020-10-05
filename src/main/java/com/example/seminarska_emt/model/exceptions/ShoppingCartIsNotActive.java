package com.example.seminarska_emt.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ShoppingCartIsNotActive extends RuntimeException {
    public ShoppingCartIsNotActive(String userId) {
        super(String.format("There is no active shopping cart for user %s!", userId));
    }
}

