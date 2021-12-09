package com.example.Library.Primitives;

public final class Username {
    private final String value;

    public Username(final String value) {
        //use regex for username
        if(true) this.value = value;
        else throw new IllegalArgumentException();
    }
    public String value() {
        return value;
    }
    public boolean equals(Username other) {
        return other != null && this.value.equals(other.value);
    }
}
