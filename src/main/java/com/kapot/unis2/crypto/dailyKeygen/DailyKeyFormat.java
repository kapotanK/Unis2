package com.kapot.unis2.crypto.dailyKeygen;

import com.kapot.unis2.crypto.util.ByteArrayUtil;

import java.math.BigInteger;
import java.util.function.Function;

public enum DailyKeyFormat {
    HEX(bytes -> ByteArrayUtil.bytesToHex(ByteArrayUtil.toPrimitives(bytes))),
    DEC(bytes -> FormatUtil.bytesToDec(ByteArrayUtil.toPrimitives(bytes))),
    DEC_SMALL(bytes -> new BigInteger(ByteArrayUtil.toPrimitives(bytes)).intValue() + ""),
    ENG(bytes -> FormatUtil.bytesToEng(ByteArrayUtil.toPrimitives(bytes)));

    private Function<Byte[], String> formatter;

    DailyKeyFormat(Function<Byte[], String> formatter) {
        this.formatter = formatter;
    }

    public String format(byte[] key) {
        return formatter.apply(ByteArrayUtil.toWraps(key));
    }
}
