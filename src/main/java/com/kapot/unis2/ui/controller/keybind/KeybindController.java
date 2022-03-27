package com.kapot.unis2.ui.controller.keybind;

import com.kapot.unis2.ui.controller.CipherSceneController;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;
import java.util.Set;

public class KeybindController {

    private CipherSceneController sceneController;
    private Set<KeyCode> pressedKeys;

    public KeybindController(CipherSceneController sceneController) {
        this.sceneController = sceneController;
        this.pressedKeys = new HashSet<>();
        sceneController.appInstance.getStage().addEventHandler(KeyEvent.KEY_PRESSED, e -> pressedKeys.add(e.getCode()));
        sceneController.appInstance.getStage().addEventHandler(KeyEvent.KEY_RELEASED, e -> pressedKeys.remove(e.getCode()));
    }

    public void addCombination(KeyCombination combination, Runnable runnable) {
        sceneController.appInstance.getStage().getScene().getAccelerators()
                .put(combination, runnable);
    }
    public void addMultipleCombination(Runnable runnable, KeyCode... codes) {
        addCombination(new MultipleKeyCombination(this, codes), runnable);
    }

    public Set<KeyCode> getPressedKeys() {
        return pressedKeys;
    }

}
