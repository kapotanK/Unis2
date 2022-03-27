package com.kapot.unis2.ui.util;

public enum TextColor {

    GREEN("#009900"),
    YELLOW("#ffaf00"),
    RED("#ca0000"),
    DEFAULT("#000000");


    String hex;

    TextColor(String hex) {
        this.hex = hex;
    }

    public String getHex() {
        return hex;
    }
}
