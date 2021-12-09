package com.example.Library.Primitives;

import static org.apache.commons.lang3.Validate.*;

public class ISBN {
    private final String value;
    public ISBN(final String value) {
        notNull(value);
        isTrue(value.length() == 10);
        isTrue(value.matches("[0-9X]*"));
        isTrue(value.matches("[0-9]{9}[0-9X]"));
        isTrue(checksumValid(value));
        this.value = value;
    }
    private boolean checksumValid(String isbn) {
        int sum = 0;
        for (int i = 1; i < isbn.length(); i++) {
            sum += i * (isbn.charAt(i - 1) - '0');
        }

        String checksum = String.valueOf(sum % 11);
        if (checksum == "10") {
            checksum = "X";
        }

        return checksum.charAt(0) == isbn.charAt(9);
    }

    public String getValue() {
        return this.value;
    }
}
