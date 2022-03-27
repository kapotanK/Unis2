package com.kapot.unis2.crypto.ciphers;

import com.kapot.unis2.crypto.ciphers.util.alphabetic.Alphabet;
import com.kapot.unis2.crypto.ciphers.util.alphabetic.CharUtil;
import com.kapot.unis2.exceptions.InputDataProcessingException;
import com.kapot.unis2.ui.wrappers.AdditionalArgsWrapper;
import com.kapot.unis2.ui.wrappers.CipherInputWrapper;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AtbashCipher extends AbstractCipher {
    public AtbashCipher() {
        super(
                "Atbash",
                false,
                true,
                true,
                false,
                false,
                Arrays.asList(
                        "Alphabetic cipher that reverses letter indexes.",
                        "f.ex. A <-> Z, B <-> Y, C <-> X, etc..."
                ),
                Arrays.asList(),
                Arrays.asList(
                        Alphabet.ALPHABET_ADDIT + "  - alphabet to use. Non-alphabetic symbols will be passed through."
                )
        );
    }


    @Override
    public byte[] encrypt(CipherInputWrapper input, AdditionalArgsWrapper addits) {
        return crypt(input.getInputDataAsString(), addits).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public byte[] decrypt(CipherInputWrapper input, AdditionalArgsWrapper addits) {
        return crypt(input.getInputDataAsString(), addits).getBytes(StandardCharsets.UTF_8);
    }

    private Alphabet.AlphabetObject getAlphabet(AdditionalArgsWrapper addits) {
        try {
            return Alphabet.fromAddits(addits);
        } catch (IllegalArgumentException ex) {
            throw new InputDataProcessingException(ex.getMessage());
        }
    }
    private String crypt(String in, AdditionalArgsWrapper addits) {
        Alphabet.AlphabetObject alphabet = getAlphabet(addits);
        StringBuilder out = new StringBuilder();
        for (char c : in.toCharArray()) {
            if (alphabet.contains(c)) {
                int index = alphabet.indexOf(c);
                char nc = alphabet.getAt(alphabet.getLastIndex() - index);
                nc = CharUtil.applyCase(c, nc);
                out.append(nc);
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }
}
