package com.cloud.spring;

import java.util.*;

/**
 * @Author zhuz
 * @Date 2020/8/8
 */
public class TestMain {

    public static void main(String[] args) {
        String str = "fdsafsadfwsetrwerqwwfdassafsdfsdafewewffsgtg";
        char[] c = str.toCharArray();
        Map<Object, Object> m = new HashMap(str.length());
        for (char s : c) {
            if (!m.containsKey(s)) {
                m.put(s, 1);
            } else {
                m.put(s, Integer.parseInt(m.get(s).toString()) + 1);
            }
        }

        for (Map.Entry<Object, Object> p : m.entrySet()) {
            System.out.println(p);
        }


    }


}
