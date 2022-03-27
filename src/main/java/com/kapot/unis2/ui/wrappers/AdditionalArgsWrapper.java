package com.kapot.unis2.ui.wrappers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AdditionalArgsWrapper {

    private Map<String, String> args;


    public AdditionalArgsWrapper(String addits) {
        this.args = new HashMap<>();

        String[] lines = addits.split("\n");
        for (String line : lines) {
            String[] split = line.split(" ");
            String key = split[0];
            if (key.startsWith("-")) {
                key = key.substring(1);
                String value = Arrays.stream(split).skip(1L).collect(Collectors.joining(" "));
                args.put(key, value);
            }
        }
    }


    public boolean hasKey(String argKey) {
        return args.containsKey(argKey);
    }
    public boolean hasArgValue(String argKey) {
        return !getArgValue(argKey).isEmpty();
    }
    public String getArgValue(String argKey) {
        return args.get(argKey);
    }
    public String getArgValueOrDefault(String argKey, String def) {
        return hasKey(argKey) ? args.get(argKey) : def;
    }

    @Override
    public String toString() {
        return "AdditionalArgsWrapper{" +
                "args=" + args +
                '}';
    }
}
