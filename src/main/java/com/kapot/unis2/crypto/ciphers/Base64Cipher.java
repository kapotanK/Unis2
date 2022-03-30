package com.kapot.unis2.crypto.ciphers;

import com.kapot.unis2.exceptions.CryptorException;
import com.kapot.unis2.ui.wrappers.AdditionalArgsWrapper;
import com.kapot.unis2.ui.wrappers.CipherInputWrapper;

import java.util.Arrays;
import java.util.Base64;

public class Base64Cipher extends AbstractCipher {
    public Base64Cipher() {
        super(
                "Base64",
                false,
                false,
                true,
                true,
                false,
                Arrays.asList(
                        "Used to transform unicode/bytes to ASCII."
                ),
                Arrays.asList(),
                Arrays.asList()
        );
    }

    @Override
    public byte[] encrypt(CipherInputWrapper input, AdditionalArgsWrapper addits) {
        return Base64.getEncoder().encode(input.getInputData());
    }

    @Override
    public byte[] decrypt(CipherInputWrapper input, AdditionalArgsWrapper addits) {
        try {
            return Base64.getDecoder().decode(input.getInputData());
        } catch (IllegalArgumentException ex) {
            throw new CryptorException(ex.getMessage());
        }
    }
}
