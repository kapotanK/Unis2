package com.kapot.unis2.crypto.ciphers.util.alphabetic;

import com.kapot.unis2.ui.wrappers.AdditionalArgsWrapper;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public enum Alphabet {

    ENG("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
    RUS("АБВГДЕЁЗЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"),
    COMBO("ABCDEFGHIJKLMNOPQRSTUVWXYZ АБВГДЕЁЗЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"),
    TEXT_KIT("ABCDEFGHIJKLMNOPQRSTUVWXYZ АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ_0123456789+-*/=.,:;\"!?()"),
    FULL("ABCDEFGHIJKLMNOPQRSTUVWXYZ АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ_0123456789+-*/=.,:;{}[]'\"!?@#№$%^&()~`|\\");


    public static final String ALPHABET_ADDIT =
            "-a <" + Arrays.stream(values()).map(Enum::toString).collect(Collectors.joining("/")) + "/C:CUSTOMALPHABET123>";

    private final AlphabetObject alphabetObject;

    Alphabet(String symbolsStr) {
        this.alphabetObject = new AlphabetObject(symbolsStr);
    }

    public AlphabetObject getAlphabetObject() {
        return alphabetObject;
    }

    public static AlphabetObject fromAddits(AdditionalArgsWrapper wrap) {
        String val = wrap.getArgValueOrDefault("a", "eng").toUpperCase(Locale.ROOT);
        try {
            return Alphabet.valueOf(val).getAlphabetObject();
        } catch (IllegalArgumentException ex) {
            if (val.startsWith("C:")) {
                return new AlphabetObject(val.substring(2));
            } else {
                throw new IllegalArgumentException("Invalid value for -a");
            }
        }
    }


    public static class AlphabetObject {

        private char[] symbols;

        public AlphabetObject(String symbolsStr) {
            this.symbols = symbolsStr.toCharArray();
        }


        public int indexOf(char c) {
            int i = 0;
            for (char sc : symbols) {
                if (Character.toUpperCase(c) == Character.toUpperCase(sc)) {
                    return i;
                }
                i++;
            }
            return -1;
        }
        public boolean contains(char c) {
            return indexOf(c) >= 0;
        }
        public char getAt(int index) {
            return symbols[index];
        }
        public int getLength() {
            return symbols.length;
        }
        public int getLastIndex() {
            return symbols.length - 1;
        }
        public char[] getSymbols() {
            return Arrays.copyOf(symbols, symbols.length);
        }

        public CyclicInt constructCyclic(char start) {
            if (!contains(start))
                throw new IllegalArgumentException("Start character not found in this alphabet");
            return new CyclicInt(0, getLastIndex(), indexOf(start));
        }

    }

}
