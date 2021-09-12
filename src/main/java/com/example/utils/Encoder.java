package com.example.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Encoder {
    public static String encode(String text){
        Base64.Encoder enc = Base64.getEncoder();
        return enc.encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }

    public static String encodeSubString(int offset, String text){
        String substring = text.substring(0, offset);
        Base64.Encoder enc = Base64.getEncoder();
        String encodedSubstring = enc.encodeToString(substring.getBytes(StandardCharsets.UTF_8));
        return encodedSubstring + text.substring(offset);
    }
}
