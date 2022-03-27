package com.kapot.unis2.crypto.util;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

    public static String stringHash(String in, String hash, boolean ascii) throws NoSuchAlgorithmException {
        return ByteArrayUtil.bytesToHex(byteHash(in, hash, ascii));
    }
    public static byte[] byteHash(String in, String hash, boolean ascii) throws NoSuchAlgorithmException {
        Charset cs = StandardCharsets.UTF_8;
        if (ascii)
            cs = StandardCharsets.US_ASCII;
        MessageDigest digest = MessageDigest.getInstance(hash);
        return digest.digest(in.getBytes(cs));
    }

}
