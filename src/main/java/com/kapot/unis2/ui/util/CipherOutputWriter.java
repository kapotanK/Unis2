package com.kapot.unis2.ui.util;

import com.kapot.unis2.Unis2;
import com.kapot.unis2.crypto.ciphers.AbstractCipher;
import com.kapot.unis2.crypto.util.ByteArrayUtil;
import com.kapot.unis2.exceptions.DataOutprintException;
import com.kapot.unis2.ui.controller.CipherSceneController;
import com.kapot.unis2.ui.wrappers.CipherInputWrapper;
import com.kapot.unis2.ui.wrappers.CipherOutputWrapper;
import javafx.scene.control.TextInputControl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class CipherOutputWriter {

    private CipherSceneController controller;

    public CipherOutputWriter(CipherSceneController controller) {
        this.controller = controller;
    }


    public void write(CipherOutputWrapper outputWrap) {
        CipherInputWrapper input = outputWrap.getInput();
        AbstractCipher.Mode mode = input.getMode();
        if (input.isFileMode()) {
            try {
                Path to = getFileWritePath(mode);
                Files.write(to, outputWrap.getOutputData());
                getPrintoutUIComponent(mode).setText(to.toString() + "\nClick to open"); //TODO: to constant
            } catch (IOException ex) {
                throw new DataOutprintException(ex.getClass().getSimpleName() + ": " + ex.getMessage());
            }
        } else {
            String outText;
            if (input.getCipher().isByteCipher() && mode == AbstractCipher.Mode.ENCRYPT)
                outText = ByteArrayUtil.bytesToHex(outputWrap.getOutputData());
            else
                outText = new String(outputWrap.getOutputData(), StandardCharsets.UTF_8);
            getPrintoutUIComponent(mode).setText(outText);
            getPrintoutUIComponent(mode).requestFocus();
        }
    }

    private TextInputControl getPrintoutUIComponent(AbstractCipher.Mode mode) {
        switch (mode) {
            case ENCRYPT:
                return controller.ecOut;
            case DECRYPT:
                return controller.dcOut;
            default:
                throw new IllegalArgumentException();
        }
    }
    private Path getFileWritePath(AbstractCipher.Mode mode) {
        switch (mode) {
            case ENCRYPT: {
                String path = controller.ecFileField.getText();
                File file = new File(path + Unis2.CRYPTOFILE_SUFFIX);
                return file.toPath();
            }
            case DECRYPT: {
                String path = controller.dcFileField.getText();
                System.out.println(path + " -> " + path.endsWith(Unis2.CRYPTOFILE_SUFFIX));
                if (path.endsWith(Unis2.CRYPTOFILE_SUFFIX))
                    path = path.substring(0, path.length() - Unis2.CRYPTOFILE_SUFFIX.length());
                else
                    path = path + ".unisdecrypt";
                File file = new File(path);
                return file.toPath();
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
    }

}
