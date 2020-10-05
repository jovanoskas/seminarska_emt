package com.example.seminarska_emt.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PasswordDoesntMatch extends RuntimeException {
    public PasswordDoesntMatch() {
        super("Password doesn't match!!!");
    }
}
