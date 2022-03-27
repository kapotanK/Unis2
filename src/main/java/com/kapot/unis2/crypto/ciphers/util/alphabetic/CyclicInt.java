package com.kapot.unis2.crypto.ciphers.util.alphabetic;

public class CyclicInt {

    int min;
    int max;
    int current;

    public CyclicInt(int min, int max, int current) {
        if (min > max)
            throw new IllegalArgumentException("Invalid cyclic int min-max");
        this.min = min;
        this.max = max;
        if (current > max || current < min)
            throw new IllegalArgumentException("Invalid cyclic int current");
        this.current = current;
    }

    public int get() {
        return this.current;
    }

    public int add(int i, boolean modify) {
        int cur = this.current;
        int range = this.max - this.min + 1;
        if (range <= 1)
            return cur;
        cur += i;
        if (cur > this.max) {
            int maxes = cur / range;
            cur = cur - range * maxes;
//            if (cur == 0)
//                cur = max;
        } else if (cur < this.min) {
            if (-cur > range) {
                int maxes = -cur / range;
                cur = cur + range * maxes;
            }
            cur = max + cur + 1;
        }
        if (modify)
            this.current = cur;
        return cur;
    }

}
