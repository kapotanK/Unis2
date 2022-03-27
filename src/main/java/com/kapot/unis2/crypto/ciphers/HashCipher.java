package com.kapot.unis2.crypto.ciphers;

import com.kapot.unis2.crypto.util.ByteArrayUtil;
import com.kapot.unis2.crypto.util.HashUtil;
import com.kapot.unis2.exceptions.CryptorException;
import com.kapot.unis2.ui.wrappers.AdditionalArgsWrapper;
import com.kapot.unis2.ui.wrappers.CipherInputWrapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class HashCipher extends AbstractCipher {
    public HashCipher() {
        super(
                "Hash",
                false,
                true,
                false,
                true,
                true,
                Arrays.asList("Generates hashsum of input data by selected algorithm"),
                Arrays.asList(),
                Arrays.asList(
                        "-h <algorithm>  - hashing algorithm to use. Default is SHA-256"
                )
        );
    }


    @Override
    public byte[] encrypt(CipherInputWrapper input, AdditionalArgsWrapper addits) {
        //boolean ascii = addits.hasKey("ascii");
        String algorithm = addits.getArgValueOrDefault("h", "SHA-256");
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            return digest.digest(input.getInputData()); //TODO: out to textarea not file
        } catch (NoSuchAlgorithmException ex) {
            throw new CryptorException("Hashing algorithm " + algorithm + " not found.");
        }
    }

    @Override
    public byte[] decrypt(CipherInputWrapper input, AdditionalArgsWrapper addits) {
        return new byte[0];
    }
}
