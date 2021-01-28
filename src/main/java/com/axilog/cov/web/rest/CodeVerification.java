package com.axilog.cov.web.rest;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeVerification {

    public static String randomVerificationCode() {
        System.out.println("Generating Password");

        String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%&?{}*";
        StringBuilder builder = new StringBuilder();

        int count = 6;

        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        System.out.println("Generated code: "+ builder.toString());
        return builder.toString();
    }
}
