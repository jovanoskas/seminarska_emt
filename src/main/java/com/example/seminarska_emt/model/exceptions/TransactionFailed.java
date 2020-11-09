package com.example.seminarska_emt.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class TransactionFailed extends RuntimeException {
    public TransactionFailed(String userId, String message) {
        super(String.format("Transaction for user %s failed! Message: %s", userId, message));
    }
}
