package com.kapot.unis2.crypto.dailyKeygen;

import com.kapot.unis2.crypto.util.HashUtil;

import java.security.NoSuchAlgorithmException;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;

public class DailyKeygen {

    private static final String VERSION_KEY = "unis2";
    private String keygenKey;

    public DailyKeygen(String keygenKey) {
        this.keygenKey = keygenKey;
    }

    public void setKeygenKey(String keygenKey) {
        this.keygenKey = keygenKey;
    }
    public String getKeygenKey() {
        return keygenKey;
    }

    public String getDayKey(DailyKeyFormat format) {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone(ZoneId.of("UTC+0")));
        String base = c.get(Calendar.YEAR) + ":" + c.get(Calendar.MONTH) + ":" + c.get(Calendar.DAY_OF_MONTH) + ":" + this.keygenKey + ":" + VERSION_KEY;
        try {
            byte[] hash = HashUtil.byteHash(base, "SHA-256", false);
            return format.format(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "HASHING ERROR, SEE CONSOLE";
        }
    }

}
