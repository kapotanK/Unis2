package com.kapot.unis2.crypto.dailyKeygen;

import com.kapot.unis2.crypto.util.ByteArrayUtil;

import java.math.BigInteger;

public class FormatUtil {

    public static String bytesToDec(byte[] in) {
        StringBuilder builder = new StringBuilder();
        for (byte b : in) {
            builder.append(Math.abs(b));
        }
        return builder.toString();
    }
    public static String bytesToEng(byte[] in) {
        String hex = ByteArrayUtil.bytesToHex(in);
        return hex
                .replaceAll("0", "g")
                .replaceAll("1", "h")
                .replaceAll("2", "i")
                .replaceAll("3", "j")
                .replaceAll("4", "k")
                .replaceAll("5", "l")
                .replaceAll("6", "m")
                .replaceAll("7", "n")
                .replaceAll("8", "o")
                .replaceAll("9", "p");
    }

}
