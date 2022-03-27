package com.kapot.unis2.crypto;

import com.kapot.unis2.crypto.ciphers.*;

import java.util.Arrays;

public enum CipherType {

    AES(new AESCipher()),
    VIGENERE(new VigenereCipher()),
    CAESAR(new CaesarCipher()),
    ATBASH(new AtbashCipher()),
    BASE64(new Base64Cipher()),
    HASH(new HashCipher()),
    A1Z26(new A1Z26Cipher());

    //TODO: RSA w/keypairs


    private AbstractCipher cipher;

    CipherType(AbstractCipher cipher) {
        this.cipher = cipher;
    }

    public AbstractCipher getCipher() {
        return cipher;
    }


    public static CipherType getByName(String name) {
        return Arrays.stream(values()).filter(c -> c.getCipher().getName().equals(name)).findFirst().get();
    }

}
