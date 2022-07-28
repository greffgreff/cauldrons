package com.greffgreff.cauldrons.utils;

public final class Console {
    public static final String PREFIX = "[CAULDRONS]";

    public static void debug(Object obj) {
        System.out.println(PREFIX + " " + (obj == null ? "NULL" : obj.toString()));
    }
}
