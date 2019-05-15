package com.kuhnwei.mohist.examples.java.algorithm.reverse;

/**
 * 反转
 * @author Kuhn Wei, email@kuhnwei.com
 * @version 2018/3/25 12:56
 */
public class Reverse {

    public static int reverseInteger(int number) {
        /*
        这是一开始时的思路
        String numberStr = String.valueOf(number);
        char[] numArray = numberStr.toCharArray();
        int temp = numArray.length;
        char[] reverArray = new char[numArray.length];
        for (int i = 0; i < numArray.length; i ++) {
            temp --;
            reverArray[temp] = numArray[i];
        }
        String reverStr = new String(reverArray);
        return Integer.valueOf(reverStr);
        */

        // 正确的思路应该如下
        int result = 0;

        while (number != 0)
        {
            int tail = number % 10;
            int newResult = result * 10 + tail;
            if ((newResult - tail) / 10 != result)
            { return 0; }
            result = newResult;
            number = number / 10;
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(reverseInteger(-1234));
    }

}
