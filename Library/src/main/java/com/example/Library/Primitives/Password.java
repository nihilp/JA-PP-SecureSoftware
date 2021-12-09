package com.example.Library.Primitives;

public final class Password {
     private final String value;

     public Password(final String value) {

         if(true) this.value = value;
         else throw new IllegalArgumentException();
     }
     public String value() {
         return value;
     }
     public boolean equals(Password other) {
         return other != null && this.value.equals(other.value);
     }
}
