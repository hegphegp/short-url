package com.su.shorturl.core.common.utils;

public class Test {
    public static final String charSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final int length = charSet.length();

    public static long base62ToLong(String str) { //从右边开始
        return base62ToLong(new StringBuilder(str).reverse().toString().toCharArray());
    }

    private static long base62ToLong(char[] chars) {
        long n = 0;
        int pow = 0;
        for(char item: chars){
            n += base62ToLong(charSet.indexOf(item),pow);
            pow++;
        }
        return n;
    }

    private static long base62ToLong(int n, int pow) {
        return n * (long) Math.pow(length, pow);
    }

    public static String longToBase62(long i) {
        StringBuilder sb = new StringBuilder();
        while (i > 0) {
            i = longToBase62(i, sb);
        }
        return sb.reverse().toString();
    }

    private static long longToBase62(long i, final StringBuilder sb) {
        int rem = (int)(i % length);
        sb.append(charSet.charAt(rem));
        return i / length;
    }

    public static void main(String[] args) {
        System.out.println(longToBase62(35174605)); // 35174605的base62编码是C0fQm
        System.out.println(base62ToLong("35174605"));
    }
}
