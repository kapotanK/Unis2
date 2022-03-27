package com.kapot.unis2.crypto.ciphers;

import com.kapot.unis2.ui.wrappers.AdditionalArgsWrapper;
import com.kapot.unis2.ui.wrappers.CipherInputWrapper;
import com.kapot.unis2.ui.wrappers.CipherOutputWrapper;

import java.util.List;

public abstract class AbstractCipher {

    private String name;

    private boolean supportsKeys;
    private boolean supportsAddits;
    private boolean supportsDecrypt;
    private boolean supportsFiles;
    private boolean byteCipher;

    private List<String> generalTooltip;
    private List<String> keyTooltip;
    private List<String> additsTooltip;


    public AbstractCipher(String name, boolean supportsKeys, boolean supportsAddits, boolean supportsDecrypt, boolean supportsFiles, boolean byteCipher, List<String> generalTooltip, List<String> keyTooltip, List<String> additsTooltip) {
        this.name = name;
        this.supportsKeys = supportsKeys;
        this.supportsAddits = supportsAddits;
        this.supportsDecrypt = supportsDecrypt;
        this.supportsFiles = supportsFiles;
        this.byteCipher = byteCipher;
        this.generalTooltip = generalTooltip;
        this.keyTooltip = keyTooltip;
        this.additsTooltip = additsTooltip;
    }


    public CipherOutputWrapper processData(CipherInputWrapper input, AdditionalArgsWrapper addits) {
        switch (input.getMode()) {
            case ENCRYPT:
                return new CipherOutputWrapper(input, encrypt(input, addits));
            case DECRYPT:
                return new CipherOutputWrapper(input, decrypt(input, addits));
            default:
                throw new IllegalArgumentException();
        }
    }
    protected abstract byte[] encrypt(CipherInputWrapper input, AdditionalArgsWrapper addits);
    protected abstract byte[] decrypt(CipherInputWrapper input, AdditionalArgsWrapper addits);


    public String getName() {
        return name;
    }

    public boolean isSupportKeys() {
        return supportsKeys;
    }

    public boolean isSupportAddits() {
        return supportsAddits;
    }

    public boolean isSupportDecrypt() {
        return supportsDecrypt;
    }

    public boolean isSupportFiles() {
        return supportsFiles;
    }

    public boolean isByteCipher() {
        return byteCipher;
    }

    public List<String> getGeneralTooltip() {
        return generalTooltip;
    }

    public List<String> getKeyTooltip() {
        return keyTooltip;
    }

    public List<String> getAdditsTooltip() {
        return additsTooltip;
    }


    public enum Mode {
        ENCRYPT,
        DECRYPT
    }

}
