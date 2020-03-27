package com.su.shorturl.core.common.utils;

import java.util.Stack;

/**
 * 数字转62进制，这里做了混淆
 */
public class Base62Number {
    //乱序（混淆）, 不要随便修改, 否则URL短链接还原Long类型会失败
    private static char[] charSet = "LBClqO43AIbTMDy8G1eVZmF0hutRkzXdQ6wNfiKEJaxWHPSg57rj9nosY2vUcp".toCharArray(); //乱序（混淆）

    /**
     * 将10进制转化为62进制
     *
     * @param number
     * @return
     */
    public static String convertDecimalToBase62(long number) {
        Long rest = number;
        Stack<Character> stack = new Stack<Character>();
        StringBuilder result = new StringBuilder(0);
        while (rest != 0) {
            stack.add(charSet[new Long((rest - (rest / 62) * 62)).intValue()]);
            rest = rest / 62;
        }
        for (; !stack.isEmpty(); ) {
            result.append(stack.pop());
        }
        StringBuilder temp0 = new StringBuilder();
        return temp0.toString() + result.toString();
    }


    /**
     * 将62进制转换成10进制数
     *
     * @param ident62
     * @return
     */
    private static String convertBase62ToDecimal(String ident62) {
        int decimal = 0;
        int base = 62;
        int keisu = 0;
        int cnt = 0;

        byte ident[] = ident62.getBytes();
        for (int i = ident.length - 1; i >= 0; i--) {
            int num = 0;
            if (ident[i] > 48 && ident[i] <= 57) {
                num = ident[i] - 48;
            } else if (ident[i] >= 65 && ident[i] <= 90) {
                num = ident[i] - 65 + 10;
            } else if (ident[i] >= 97 && ident[i] <= 122) {
                num = ident[i] - 97 + 10 + 26;
            }
            keisu = (int) java.lang.Math.pow((double) base, (double) cnt);
            decimal += num * keisu;
            cnt++;
        }
        return String.format("%10d", decimal);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String result = convertDecimalToBase62(35174605);
        System.out.println("35174605的base64编码是"+result);
        System.out.println(result+"还原后的long是"+convertBase62ToDecimal(result));
    }

}
