package com.example.Library.Primitives;

import static java.lang.String.valueOf;

public class Name {
    private final String PATTERN = "^[A-Z][a-z]*$";
    private final int MAX_LENGTH = 20;
    private final String value;

    public Name(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Name cannot exceed " + valueOf(MAX_LENGTH) + " characters");
        }
        if(value.matches(PATTERN)) {
            this.value = value;
        }
        else {
            throw new IllegalArgumentException("Name can only include alphabetic characters");
        }
    }

    public String getValue() {
        return value;
    }
}
