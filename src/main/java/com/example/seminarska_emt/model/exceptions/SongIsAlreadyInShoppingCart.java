package com.example.seminarska_emt.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
public class SongIsAlreadyInShoppingCart extends RuntimeException {
    public SongIsAlreadyInShoppingCart(String songName) {
        super(String.format("Song %s is already in active shopping cart", songName));
    }
}

