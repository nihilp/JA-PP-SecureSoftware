package com.example.Library.Publisher;

public class PublisherNotFoundException extends RuntimeException {

    public PublisherNotFoundException(Long id) {
        super("Could not find publisher " + id);
    }
}
