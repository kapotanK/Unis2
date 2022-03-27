package com.kapot.unis2.ui.wrappers;

import com.kapot.unis2.crypto.ciphers.AbstractCipher;
import com.kapot.unis2.crypto.util.ByteArrayUtil;
import com.kapot.unis2.ui.controller.CipherSceneController;

import java.nio.charset.StandardCharsets;

public class CipherInputWrapper {

    private AbstractCipher.Mode mode;
    private byte[] inputData;
    private byte[] key;
    private AbstractCipher cipher;
    private boolean fileMode;

    private CipherSceneController sceneController;

    public CipherInputWrapper(AbstractCipher.Mode mode, byte[] inputData, byte[] key, AbstractCipher cipher, boolean fileMode, CipherSceneController sceneController) {
        this.mode = mode;
        this.inputData = inputData;
        this.key = key;
        this.cipher = cipher;
        this.fileMode = fileMode;
        this.sceneController = sceneController;
    }


    public AbstractCipher.Mode getMode() {
        return mode;
    }

    public byte[] getInputData() {
        if (mode == AbstractCipher.Mode.DECRYPT && cipher.isByteCipher() && !fileMode) // If input is in hexformat
            return ByteArrayUtil.hexToBytes(getInputDataAsString());
        return inputData;
    }
    public String getInputDataAsString() {
        return new String(inputData, StandardCharsets.UTF_8);
    }

    public byte[] getKey() {
        return key;
    }
    public String getKeyAsString() {
        return new String(key, StandardCharsets.UTF_8);
    }

    public AbstractCipher getCipher() {
        return cipher;
    }

    public boolean isFileMode() {
        return fileMode;
    }

    public CipherSceneController getSceneController() {
        return sceneController;
    }
}
