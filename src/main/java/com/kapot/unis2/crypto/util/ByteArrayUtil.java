package com.kapot.unis2.crypto.util;

import java.util.Arrays;

public class ByteArrayUtil {

    //Why there is no builtin alternatives in java?!

    public static byte[] toPrimitives(Byte[] wraps) {
        byte[] primitives = new byte[wraps.length];
        int i = 0;
        for (byte b : wraps) {
            primitives[i] = b;
            i++;
        }
        return primitives;
    }
    public static Byte[] toWraps(byte[] primitives) {
        Byte[] wraps = new Byte[primitives.length];
        Arrays.setAll(wraps, i -> primitives[i]);
        return wraps;
    }

    public static byte[] concatArrays(byte[]... arrays) {
        int summarySize = 0;
        for (byte[] arr : arrays) {
            summarySize += arr.length;
        }
        byte[] out = new byte[summarySize];
        int i = 0;
        for (byte[] arr : arrays) {
            for (byte b : arr) {
                out[i] = b;
                i++;
            }
        }
        return out;
    }


    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xff & bytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
        //return String.format("%x", new BigInteger(bytes)); //Sometimes gives invalid hex
    }
    public static byte[] hexToBytes(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

}
