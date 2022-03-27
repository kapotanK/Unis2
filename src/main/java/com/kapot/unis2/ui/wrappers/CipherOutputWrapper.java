package com.kapot.unis2.ui.wrappers;

public class CipherOutputWrapper {

    private CipherInputWrapper input;
    private byte[] outputData;

    public CipherOutputWrapper(CipherInputWrapper input, byte[] outputData) {
        this.input = input;
        this.outputData = outputData;
    }

    public CipherInputWrapper getInput() {
        return input;
    }
    public byte[] getOutputData() {
        return outputData;
    }

}
