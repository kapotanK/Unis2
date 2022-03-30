package com.kapot.unis2.crypto.ciphers;

import com.kapot.unis2.exceptions.CryptorException;
import com.kapot.unis2.ui.wrappers.AdditionalArgsWrapper;
import com.kapot.unis2.ui.wrappers.CipherInputWrapper;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AESCipher extends AbstractCipher {
    public AESCipher() {
        super(
                "AES",
                true,
                false,
                true,
                true,
                true,
                Arrays.asList(
                        "At this moment it is the most safe cipher in Unis.",
                        "Can crypt UTF-8 text & any files (>500MB files is not recommended)",
                        "Uses SHA-256 hashsum of keystring as cipher key, returns result bytes to file (filemode) or as hex string."
                ),
                Arrays.asList("Any UTF-8 string"),
                Arrays.asList()
        );
    }


    @Override
    public byte[] encrypt(CipherInputWrapper input, AdditionalArgsWrapper addits) { //TODO: partial crypting for large files
        try {
            Key k = createKey(input.getKey());
            Cipher c = getCipher(Cipher.ENCRYPT_MODE, k);

//            System.out.println("aes cinpd = " + Arrays.toString(input.getInputData()));
//            System.out.println("aes coutd = " + Arrays.toString(c.doFinal(input.getInputData())));
            return c.doFinal(input.getInputData());

        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
            throw new CryptorException("Can't find AES, lol. See console.");
        } catch (InvalidKeyException e) {
            throw new CryptorException("Invalid key.");
        } catch (BadPaddingException | IllegalBlockSizeException | StringIndexOutOfBoundsException e) {
            throw new CryptorException("Invalid key or input.");
        }
    }

    @Override
    public byte[] decrypt(CipherInputWrapper input, AdditionalArgsWrapper addits) {
        try {
            Key k = createKey(input.getKey());
            Cipher c = getCipher(Cipher.DECRYPT_MODE, k);

//            System.out.println("aes dcinpd = " + Arrays.toString(input.getInputData()));
//            System.out.println("aes dcoutd = " + Arrays.toString(c.doFinal(input.getInputData())));
            return c.doFinal(input.getInputData());

        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
            throw new CryptorException("Can't find AES, lol. See console.");
        } catch (InvalidKeyException e) {
            throw new CryptorException("Invalid key.");
        } catch (BadPaddingException | IllegalBlockSizeException | StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            throw new CryptorException("Invalid key or input.");
        }
    }


    private SecretKeySpec createKey(byte[] keyBytes) throws NoSuchAlgorithmException {
        MessageDigest kg = MessageDigest.getInstance("SHA-256");
        byte[] hashedKey = kg.digest(keyBytes);
        return new SecretKeySpec(hashedKey, "AES");
    }
    private Cipher getCipher(int mode, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher c = Cipher.getInstance("AES");
        c.init(mode, key);
        return c;
    }
}
