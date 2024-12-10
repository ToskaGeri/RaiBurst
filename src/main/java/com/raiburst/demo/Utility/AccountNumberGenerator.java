package com.raiburst.demo.Utility;

import java.security.SecureRandom;

public class AccountNumberGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generate() {
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            accountNumber.append(CHARACTERS.charAt(index));
        }
        return accountNumber.toString();
    }
}