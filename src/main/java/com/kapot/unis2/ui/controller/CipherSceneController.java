package com.kapot.unis2.ui.controller;

import com.kapot.unis2.Unis2;
import com.kapot.unis2.crypto.CipherType;
import com.kapot.unis2.crypto.ciphers.AbstractCipher;
import com.kapot.unis2.crypto.dailyKeygen.DailyKeyFormat;
import com.kapot.unis2.crypto.dailyKeygen.DailyKeygen;
import com.kapot.unis2.exceptions.InputDataProcessingException;
import com.kapot.unis2.exceptions.UnisException;
import com.kapot.unis2.ui.controller.keybind.KeybindController;
import com.kapot.unis2.ui.util.CipherOutputWriter;
import com.kapot.unis2.ui.util.TextColor;
import com.kapot.unis2.ui.wrappers.AdditionalArgsWrapper;
import com.kapot.unis2.ui.wrappers.CipherInputWrapper;
import com.kapot.unis2.ui.wrappers.CipherOutputWrapper;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CipherSceneController {

    public Unis2 appInstance;

    public Button dkgOut;
    public TextField dkgShownKey;
    public PasswordField dkgHiddenKey;
    public ComboBox<String> dkgOutFormat;
    public CheckBox dkgShowKeyCheckbox;
    public Label dkgCopystatusLabel;
    public Label dkgCopystatusHider;

    public ComboBox<String> cipherChooser;
    public Tooltip cipherTooltip;

    public TextArea ecIn;
    public GridPane ecFileGrid;
    public Button ecFileChooser;
    public TextField ecFileField;
    public CheckBox ecFileModeCheckbox;
    public TextArea ecOut;
    public TextField ecKey;
    public Tooltip ecKeyTooltip;
    public TextArea ecAddits;
    public Tooltip ecAdditsTooltip;
    public Button ecBtn;
    public Label ecStatus;

    public GridPane dcGrid;
    public TextArea dcIn;
    public GridPane dcFileGrid;
    public Button dcFileChooser;
    public TextField dcFileField;
    public CheckBox dcFileModeCheckbox;
    public TextArea dcOut;
    public TextField dcKey;
    public Tooltip dcKeyTooltip;
    public TextArea dcAddits;
    public Tooltip dcAdditsTooltip;
    public Button dcBtn;
    public Label dcStatus;

    protected DailyKeygen keygen;
    protected CipherType currentCipherType;


    // Keygen zone logic
    public void onKeygenOutButtonClicked() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(dkgOut.getText()), null);

        dkgOut.setDisable(true);
        dkgCopystatusLabel.setVisible(true);
        dkgCopystatusHider.setVisible(false);
        appInstance.getThreadManager().runLater(() -> {
            dkgOut.setDisable(false);
            dkgCopystatusLabel.setVisible(false);
            dkgCopystatusHider.setVisible(true);
        }, 1000L);
    }
    public void onKeygenOutTypeChanged() {
        updateDailyKey();
    }
    public void onKeygenKeyChanged() {
        keygen.setKeygenKey(dkgShownKey.getText());
        updateDailyKey();
    }
    public void onKeygenShowKeyClicked() {
        boolean show = dkgShowKeyCheckbox.isSelected();
        dkgHiddenKey.setVisible(!show);
        dkgShownKey.setVisible(show);
    }
    protected DailyKeyFormat getDailyKeyFormat() {
        return DailyKeyFormat.valueOf(dkgOutFormat.getSelectionModel().getSelectedItem());
    }
    protected void updateDailyKey() {
        dkgOut.setText(keygen.getDayKey(getDailyKeyFormat()));
    }

    // cipher chooser logic
    public void onCipherChanged() {
        changeCipher(CipherType.getByName(cipherChooser.getSelectionModel().getSelectedItem()));
    }
    public void changeCipher(CipherType cipherType) {
        currentCipherType = cipherType;

        // For better code-readablity
        boolean supportKeys = cipherType.getCipher().isSupportKeys();
        boolean supportAddits = cipherType.getCipher().isSupportAddits();
        boolean supportDecrypt = cipherType.getCipher().isSupportDecrypt();
        boolean supportFiles = cipherType.getCipher().isSupportFiles();
        //boolean byteCipher = cipherType.getCipher().isByteCipher();
        List<String> generalTip = cipherType.getCipher().getGeneralTooltip();
        List<String> keyTip = cipherType.getCipher().getKeyTooltip();
        List<String> additTip = cipherType.getCipher().getAdditsTooltip();

        // Selecting in chooser
        cipherChooser.getSelectionModel().select(cipherType.getCipher().getName());
        cipherTooltip.setText(String.join("\n", generalTip));

        // Working with cipher parameters
        setFieldState(ecKey, supportKeys);
        setFieldState(ecAddits, supportAddits);
        setTooltipState(ecKeyTooltip, supportKeys, keyTip);
        setTooltipState(ecAdditsTooltip, supportAddits, additTip);
        setFieldState(dcKey, supportKeys);
        setFieldState(dcAddits, supportAddits);
        setTooltipState(dcKeyTooltip, supportKeys, keyTip);
        setTooltipState(dcAdditsTooltip, supportAddits, additTip);
        setFilemodeState(supportFiles);

        setDecryptorState(supportDecrypt);

        // Reset out areas
        ecOut.setText("");
        dcOut.setText("");
    }
    protected void setFieldState(TextInputControl field, boolean supported) {
        if (supported) {
            field.setPromptText("");
            field.setDisable(false);
        } else {
            field.setPromptText("Not supported");
            field.setDisable(true);
        }
    }
    protected void setTooltipState(Tooltip tip, boolean supported, List<String> text) {
        if (supported) {
            tip.setText(String.join("\n", text));
        } else {
            tip.setText("");
        }
    }
    protected void setFilemodeState(boolean supported) {
        ecFileGrid.setDisable(!supported);
        dcFileGrid.setDisable(!supported);
        if (!supported) {
            ecFileModeCheckbox.setSelected(false);
            dcFileModeCheckbox.setSelected(false);
            onEncryptFileModeCheckboxChanged();
            onDecryptFileModeCheckboxChanged();
        }
    }
    protected void setDecryptorState(boolean enabled) {
        dcGrid.setDisable(!enabled);

        if (!enabled) {
            dcKeyTooltip.setText("");
            dcAdditsTooltip.setText("");
            setDecryptStatus("No decryptor for this cipher.", TextColor.DEFAULT);
        } else {
            setDecryptStatus("OK", TextColor.GREEN);
        }
    }

    // File/text mode management
    public void onEncryptFileModeCheckboxChanged() {
        setFileMode(ecIn, ecFileChooser, ecFileField, isFileModeEncrypt());
    }
    protected boolean isFileModeEncrypt() {
        return ecFileModeCheckbox.isSelected() && currentCipherType.getCipher().isSupportFiles();
    }

    public void onDecryptFileModeCheckboxChanged() {
        setFileMode(dcIn, dcFileChooser, dcFileField, isFileModeDecrypt());
    }
    protected boolean isFileModeDecrypt() {
        return dcFileModeCheckbox.isSelected() && currentCipherType.getCipher().isSupportFiles();
    }

    public void onEncryptChooseFileClicked() {
        chooseFile(ecFileField, false);
    }
    public void onDecryptChooseFileClicked() {
        if (!currentCipherType.getCipher().isSupportDecrypt())
            return;
        chooseFile(dcFileField, true);
    }

    protected void setFileMode(TextArea in, Button fileChooser, TextField fileField, boolean fileMode) {
        in.setDisable(fileMode);
        fileChooser.setDisable(!fileMode);
        fileField.setDisable(!fileMode);
    }
    protected void chooseFile(TextField to, boolean forDecrypt) {
        FileChooser fc = new FileChooser();
        File initDir = new File(to.getText());
        if (initDir.exists()) {
            if (initDir.isFile()) {
                initDir = initDir.getParentFile();
            }
            fc.setInitialDirectory(initDir);
        } else {
            fc.setInitialDirectory(new File("D:"));
        }
        if (forDecrypt)
            fc.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(
                            "Unis-crypted files",
                            "*" + Unis2.CRYPTOFILE_SUFFIX
                    )
            );
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(
                        "Any files",
                        "*.*"
                )
        );
        File file = fc.showOpenDialog(appInstance.getStage());
        if (file != null)
            to.setText(file.getPath());

//        try {
//            Desktop.getDesktop().open(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    // Encrypting & Decrypting
    protected byte[] getBytesToEncrypt() {
        if (isFileModeEncrypt()) {
            return getFileBytes(ecFileField.getText());
        } else {
            return ecIn.getText().getBytes(StandardCharsets.UTF_8);
        }
    }
    protected byte[] getBytesToDecrypt() {
        if (isFileModeDecrypt()) {
            return getFileBytes(dcFileField.getText());
        } else {
            return dcIn.getText().getBytes(StandardCharsets.UTF_8);
        }
    }
    protected byte[] getFileBytes(String path) {
        try {
            File file = new File(path);
            if (!file.exists())
                throw new InputDataProcessingException("File not found");
            if (file.isDirectory())
                throw new InputDataProcessingException("Selected file is a directory");
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new InputDataProcessingException(e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    public void onEncryptButtonClick() {
        try {
            ecOut.setText("");
            
            setEncryptStatus("Processing input data...", TextColor.YELLOW);
            byte[] in = getBytesToEncrypt();
            byte[] key = ecKey.getText().getBytes(StandardCharsets.UTF_8);
            CipherInputWrapper inputWrap = new CipherInputWrapper(
                    AbstractCipher.Mode.ENCRYPT,
                    in,
                    key,
                    currentCipherType.getCipher(),
                    isFileModeEncrypt(),
                    this
            );
            AdditionalArgsWrapper additsWrap = new AdditionalArgsWrapper(ecAddits.getText());
            
            setEncryptStatus("Encrypting...", TextColor.YELLOW);
            CipherOutputWrapper out = currentCipherType.getCipher().processData(inputWrap, additsWrap);
            
            setEncryptStatus("Printing out...", TextColor.YELLOW);
            new CipherOutputWriter(this).write(out);
            
            setEncryptStatus("Done", TextColor.GREEN);
        } catch (UnisException ex) {
            setEncryptStatus(ex.getMessage(), TextColor.RED);
        }
    }
    public void onDecryptButtonClick() {
        if (!currentCipherType.getCipher().isSupportDecrypt())
            return;
        try { //TODO: encrypt-decrypt to parallel thread
            dcOut.setText("");

            setDecryptStatus("Processing input data...", TextColor.YELLOW);
            byte[] in = getBytesToDecrypt();
            byte[] key = dcKey.getText().getBytes(StandardCharsets.UTF_8);
            CipherInputWrapper inputWrap = new CipherInputWrapper(
                    AbstractCipher.Mode.DECRYPT,
                    in,
                    key,
                    currentCipherType.getCipher(),
                    isFileModeDecrypt(),
                    this
            );
            AdditionalArgsWrapper additsWrap = new AdditionalArgsWrapper(dcAddits.getText());

            setDecryptStatus("Decrypting...", TextColor.YELLOW);
            CipherOutputWrapper out = currentCipherType.getCipher().processData(inputWrap, additsWrap);

            setDecryptStatus("Printing out...", TextColor.YELLOW);
            new CipherOutputWriter(this).write(out);

            setDecryptStatus("Done", TextColor.GREEN);
        } catch (UnisException ex) {
            setDecryptStatus(ex.getMessage(), TextColor.RED);
        }
    }


    // Other methods
    public void setEncryptStatus(String status, TextColor color) {
        ecStatus.setText(status);
        ecStatus.setTextFill(Color.web(color.getHex()));
    }
    public void setDecryptStatus(String status, TextColor color) {
        dcStatus.setText(status);
        dcStatus.setTextFill(Color.web(color.getHex()));
    }


    public void onEncryptOutAreaClicked() throws IOException {
        if (isFileModeEncrypt()) {
            openFile(ecOut);
        }
    }
    public void onDecryptOutAreaClicked() throws IOException {
        if (isFileModeDecrypt()) {
            openFile(dcOut);
        }
    }
    private void openFile(TextArea dcOut) throws IOException {
        if (!dcOut.getText().isEmpty()) {

            String path = dcOut.getText().split("\n")[0];
            File file = new File(path);
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException ex) {
                Desktop.getDesktop().open(file.getParentFile());
            }

        }
    }


    // Controller initialization
    public void init(Unis2 appInstance) { //TODO: disable tooltip autohide
        this.appInstance = appInstance;

        // Set up keygen
        keygen = new DailyKeygen("");

        // Set up keygen key field(s)
        dkgShownKey.textProperty().addListener((obs, old, upd) -> {
            processKeygenKeyChange(true);
        });
        dkgHiddenKey.textProperty().addListener((obs, old, upd) -> {
            processKeygenKeyChange(false);
        });
        dkgHiddenKey.textProperty().bindBidirectional(dkgShownKey.textProperty());

        // Keygen output format combobox
        dkgOutFormat.getItems().addAll(
                Arrays.stream(DailyKeyFormat.values())
                        .map(Enum::name)
                        .collect(Collectors.toList())
        );
        dkgOutFormat.getSelectionModel().select(0);

        // Generate daykey
        updateDailyKey();

        // Fill chooser with ciphers
        cipherChooser.getItems().addAll(
                Arrays.stream(CipherType.values())
                        .map(c -> c.getCipher().getName())
                        .collect(Collectors.toList())
        );

        // Apply first cipher to scene
        changeCipher(CipherType.values()[0]);

        // Initialize keybinds
        initKeybinds();
    }
    protected void processKeygenKeyChange(boolean shownKeyField) {
        if (dkgShowKeyCheckbox.isSelected() && shownKeyField)
            onKeygenKeyChanged();
        else if (!dkgShowKeyCheckbox.isSelected() && !shownKeyField)
            onKeygenKeyChanged();
    }
    
    
    protected void initKeybinds() {
        KeybindController kbs = new KeybindController(this);

        kbs.addCombination(KeyCombination.keyCombination("CTRL+E"), this::onEncryptButtonClick);
        kbs.addCombination(KeyCombination.keyCombination("CTRL+D"), this::onDecryptButtonClick);

        kbs.addCombination(KeyCombination.keyCombination("ALT+E"), () -> {
            if (isFileModeEncrypt())
                onEncryptChooseFileClicked();
            else
                ecIn.requestFocus();
        });
        kbs.addCombination(KeyCombination.keyCombination("ALT+D"), () -> {
            if (isFileModeDecrypt())
                onDecryptChooseFileClicked();
            else
                dcIn.requestFocus();
        });

//        kbs.addMultipleCombination(() -> { //TODO:
//            ecFileModeCheckbox.setSelected(!ecFileModeCheckbox.isSelected());
//            onEncryptFileModeCheckboxChanged();
//        }, KeyCode.ALT, KeyCode.E, KeyCode.F);
//        kbs.addMultipleCombination(() -> {
//            dcFileModeCheckbox.setSelected(!dcFileModeCheckbox.isSelected());
//            onDecryptFileModeCheckboxChanged();
//        }, KeyCode.ALT, KeyCode.D, KeyCode.F);


    }

}
