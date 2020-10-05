package com.example.seminarska_emt.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class SongNotFound extends RuntimeException {
    public SongNotFound(Long id) {
        super(String.format("Song with %d was not found!", id));
    }
}

