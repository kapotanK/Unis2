package com.kapot.unis2.crypto.ciphers;

import com.kapot.unis2.crypto.ciphers.util.alphabetic.Alphabet;
import com.kapot.unis2.crypto.ciphers.util.alphabetic.CharUtil;
import com.kapot.unis2.crypto.ciphers.util.alphabetic.CyclicInt;
import com.kapot.unis2.exceptions.InputDataProcessingException;
import com.kapot.unis2.ui.wrappers.AdditionalArgsWrapper;
import com.kapot.unis2.ui.wrappers.CipherInputWrapper;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class VigenereCipher extends AbstractCipher {
    public VigenereCipher() {
        super(
                "Vigenere",
                true,
                true,
                true,
                false,
                false,
                Arrays.asList(
                        "Alphabetic cipher with dynamic shift.",
                        "f.ex: key=ABC, plain=HELLO WORLD",
                        "ABCABCABCA - every key letter makes shift = its index in alphabet",
                        "HELLOWORLD",
                        "Shift 0(A) to H -> H, 1(B) to E -> F, 2(C) to L -> N, etc",
                        "HFNLPYOSND"
                ),
                Arrays.asList("String - must contain only symbols from selected alphabet"),
                Arrays.asList(
                        Alphabet.ALPHABET_ADDIT + "  - alphabet to use.","" +
                                "           Non-alphabetic symbols in text will be passed through.",
                                "           Non-alphabetic symbols in key will cause error"
                )
        );
    }

    @Override
    public byte[] encrypt(CipherInputWrapper input, AdditionalArgsWrapper addits) {
        return crypt(
                input.getInputDataAsString(),
                input.getKeyAsString(),
                addits,
                false
        ).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public byte[] decrypt(CipherInputWrapper input, AdditionalArgsWrapper addits) {
        return crypt(
                input.getInputDataAsString(),
                input.getKeyAsString(),
                addits,
                true
        ).getBytes(StandardCharsets.UTF_8);
    }


    private Alphabet.AlphabetObject getAlphabet(AdditionalArgsWrapper addits) {
        try {
            return Alphabet.fromAddits(addits);
        } catch (IllegalArgumentException ex) {
            throw new InputDataProcessingException(ex.getMessage());
        }
    }
    private String crypt(String input, String key, AdditionalArgsWrapper addits, boolean decrypt) {
        Alphabet.AlphabetObject alphabet = getAlphabet(addits);

        char[] keyChars = key.toCharArray();
        CyclicInt keyCycle = new CyclicInt(0, key.length() - 1, 0);
        StringBuilder outBuilder = new StringBuilder();
        for (char c : input.toCharArray()) {

            char keyChar = keyChars[keyCycle.get()];
            int shift = alphabet.indexOf(keyChar);

            if (alphabet.contains(c)) {
                CyclicInt cyclic = alphabet.constructCyclic(c);
                cyclic.add((decrypt ? -shift : shift), true);
                char outChar = CharUtil.applyCase(c, alphabet.getAt(cyclic.get())); //TODO: custom case algs in args
                outBuilder.append(outChar);
            } else {
                outBuilder.append(c);
            }

            keyCycle.add(1, true);
        }
        return outBuilder.toString();
    }

}
