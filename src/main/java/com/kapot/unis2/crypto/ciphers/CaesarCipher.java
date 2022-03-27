package com.kapot.unis2.crypto.ciphers;

import com.kapot.unis2.crypto.ciphers.util.alphabetic.Alphabet;
import com.kapot.unis2.crypto.ciphers.util.alphabetic.CharUtil;
import com.kapot.unis2.crypto.ciphers.util.alphabetic.CyclicInt;
import com.kapot.unis2.exceptions.InputDataProcessingException;
import com.kapot.unis2.ui.wrappers.AdditionalArgsWrapper;
import com.kapot.unis2.ui.wrappers.CipherInputWrapper;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class CaesarCipher extends AbstractCipher {
    public CaesarCipher() {
        super(
                "Caesar",
                true,
                true,
                true,
                false,
                false,
                Arrays.asList(
                        " Alphabetic cipher with fixed shift value for all letters.",
                        "f.ex., shift=2 makes A -> C, B -> D, Z -> B, etc..."
                ),
                Arrays.asList(
                        "Shift value - any integer"
                ),
                Arrays.asList(
                        Alphabet.ALPHABET_ADDIT + "  - alphabet to use. Non-alphabetic symbols will be passed through.",
                        "-bf  - bruteforce ciphertext (only on decrypt)"
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
        if (addits.hasKey("bf")) {
            Alphabet.AlphabetObject alphabet = getAlphabet(addits);
            StringBuilder builder = new StringBuilder("BRUTEFORCE MODE:\n\n");
            for (int i = 0; i < alphabet.getLength(); i++) {
                builder.append("Key: " + i + "\n");
                builder.append(crypt(
                        input.getInputDataAsString(),
                        i + "",
                        addits,
                        true
                ));
                builder.append("\n\n");
            }
            return builder.toString().getBytes(StandardCharsets.UTF_8);
        } else {
            return crypt(
                    input.getInputDataAsString(),
                    input.getKeyAsString(),
                    addits,
                    true
            ).getBytes(StandardCharsets.UTF_8);
        }
    }

    private Alphabet.AlphabetObject getAlphabet(AdditionalArgsWrapper addits) {
        try {
            return Alphabet.fromAddits(addits);
        } catch (IllegalArgumentException ex) {
            throw new InputDataProcessingException(ex.getMessage());
        }
    }
    private String crypt(String input, String key, AdditionalArgsWrapper addits, boolean decrypt) {
        int shift;
        try {
            shift = Integer.parseInt(key);
        } catch (NumberFormatException ex) {
            throw new InputDataProcessingException("Key must be an integer");
        }
        Alphabet.AlphabetObject alphabet = getAlphabet(addits);

        StringBuilder outBuilder = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (alphabet.contains(c)) {
                CyclicInt cyclic = alphabet.constructCyclic(c);
                cyclic.add((decrypt ? -shift : shift), true);
                char outChar = CharUtil.applyCase(c, alphabet.getAt(cyclic.get())); //TODO: custom case algs in args
                outBuilder.append(outChar);
            } else {
                outBuilder.append(c);
            }
        }
        return outBuilder.toString();
    }

}
