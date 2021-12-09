package com.example.Library.Primitives;

public class BookTitle {
    private final String PATTERN = "^[.@&]?[a-zA-Z0-9 ]+[ !.@&()]?[ a-zA-Z0-9!()]+$";
    private final int MAX_LENGTH = 160;
    private final String value;

    public BookTitle(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Publisher name cannot be null");
        }
        if(value.matches(PATTERN)) {
            this.value = value;
        }
        else {
            throw new IllegalArgumentException("Publisher name must start with alphanumeric characters and only include special characters !.@&()");
        }
    }

    public String getValue() {
        return value;
    }
}
