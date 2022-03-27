package com.kapot.unis2.crypto.ciphers.util.alphabetic;

public class CharUtil {

    public static char applyCase(char from, char to) {
        if (Character.isLowerCase(from))
            return Character.toLowerCase(to);
        else
            return Character.toUpperCase(to);
    }

}
