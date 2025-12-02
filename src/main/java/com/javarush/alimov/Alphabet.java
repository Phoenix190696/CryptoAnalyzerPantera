package com.javarush.alimov;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Alphabet {
    public static final String rusAlphabet ="абвгдеёжзийклмнопрстуфцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФЦЧШЩЪЫЬЭЮЯ";
    public static final char[] chars= rusAlphabet.toCharArray();
    public static final int size=chars.length;
    public static final Map<Character, Integer> index=new HashMap<>();
    static{
        for(int i=0;i<chars.length;i++){
            index.put(chars[i],i);
        }
    }


}
