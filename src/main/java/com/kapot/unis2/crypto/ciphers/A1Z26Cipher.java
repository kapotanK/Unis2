package com.kapot.unis2.crypto.ciphers;

import com.kapot.unis2.crypto.ciphers.util.alphabetic.Alphabet;
import com.kapot.unis2.exceptions.InputDataProcessingException;
import com.kapot.unis2.ui.wrappers.AdditionalArgsWrapper;
import com.kapot.unis2.ui.wrappers.CipherInputWrapper;
import com.kapot.unis2.ui.wrappers.CipherOutputWrapper;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class A1Z26Cipher extends AbstractCipher {
    public A1Z26Cipher() {
        super(
                "A1Z26",
                false,
                true,
                true,
                false,
                false,
                Arrays.asList(
                        "Alphabetic cipher that replaces letters with they numbers in alphabet.",
                        "f.ex. ABCZ -> 1-2-3-26"
                ),
                Arrays.asList(),
                Arrays.asList(
                        Alphabet.ALPHABET_ADDIT + "  - alphabet to use. Non-alphabetic symbols will be passed through."
                )
        );
    }


    @Override
    public byte[] encrypt(CipherInputWrapper input, AdditionalArgsWrapper addits) {
        String in = input.getInputDataAsString();
        Alphabet.AlphabetObject alphabet = getAlphabet(addits);
        String splitter = addits.getArgValueOrDefault("s", "-");

        StringBuilder sb = new StringBuilder();
        boolean prevConverted = false;
        for (char c : in.toCharArray()) {
            if (alphabet.contains(c)) {
                if (sb.length() != 0 && prevConverted)
                    sb.append(splitter);
                sb.append(alphabet.indexOf(c) + 1);
                prevConverted = true;
            } else {
                sb.append(c);
                prevConverted = false;
            }
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public byte[] decrypt(CipherInputWrapper input, AdditionalArgsWrapper addits) {
        String in = input.getInputDataAsString();
        Alphabet.AlphabetObject alphabet = getAlphabet(addits);
        String splitter = addits.getArgValueOrDefault("s", "-");

        String defSplitted = in.replace(splitter, "-").replaceAll("\\d+", "-$0-");

        StringBuilder sb = new StringBuilder();
        for (String literal : defSplitted.split("-")) {
            try {
                int i = Integer.parseInt(literal);
                if (alphabet.getLength() >= i) {
                    sb.append(alphabet.getAt(i - 1));
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                sb.append(literal);
            }
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }


    private Alphabet.AlphabetObject getAlphabet(AdditionalArgsWrapper addits) {
        try {
            return Alphabet.fromAddits(addits);
        } catch (IllegalArgumentException ex) {
            throw new InputDataProcessingException(ex.getMessage());
        }
    }
}
