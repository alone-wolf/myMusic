package com.wh.mymusic.util;

public class TextUtils {
    public static String makeFirstLetterUpperCase(String name) {
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }
}
