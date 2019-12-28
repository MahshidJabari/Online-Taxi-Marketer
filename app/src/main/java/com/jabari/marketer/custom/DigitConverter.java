package com.jabari.marketer.custom;

public class DigitConverter {

    private static String[] persianNumbers = new String[]{ "۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹" };

    public static String convert(String faNumbers) {
        String[][] mChars = new String[][]{
                {"0", "۰"},
                {"1", "۱"},
                {"2", "۲"},
                {"3", "۳"},
                {"4", "۴"},
                {"5", "۵"},
                {"6", "۶"},
                {"7", "۷"},
                {"8", "۸"},
                {"9", "۹"}
        };

        for (String[] num : mChars) {

            faNumbers = faNumbers.replace(num[0], num[1]);

        }

        return faNumbers;
    }}