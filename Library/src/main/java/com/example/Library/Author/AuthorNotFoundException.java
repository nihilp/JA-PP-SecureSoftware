package com.example.Library.Author;

public class AuthorNotFoundException extends RuntimeException {

    public AuthorNotFoundException(Long id) {
        super("Could not find author " + id);
    }
}
