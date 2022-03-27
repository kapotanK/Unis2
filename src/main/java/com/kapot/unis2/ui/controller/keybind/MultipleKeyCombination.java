package com.kapot.unis2.ui.controller.keybind;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class MultipleKeyCombination extends KeyCombination {

    private KeybindController controller;
    private Set<KeyCode> codes;

    public MultipleKeyCombination(KeybindController controller, KeyCode... codes) { //TODO: string combo parser
        this.controller = controller;
        this.codes = Arrays.stream(codes).collect(Collectors.toSet());
    }

    @Override
    public boolean match(KeyEvent event) {
        Set<KeyCode> actualCodes = new HashSet<>(controller.getPressedKeys());
        actualCodes.add(event.getCode());
        //System.out.println("match? " + actualCodes + " : " + codes + " " + actualCodes.containsAll(codes));
        return actualCodes.containsAll(codes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MultipleKeyCombination that = (MultipleKeyCombination) o;
        return Objects.equals(controller, that.controller) && Objects.equals(codes, that.codes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), controller, codes);
    }

}
