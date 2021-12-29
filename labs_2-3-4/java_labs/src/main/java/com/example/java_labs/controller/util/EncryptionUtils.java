package com.example.java_labs.controller.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class EncryptionUtils {

    public static String generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Arrays.toString(salt);
    }

    public static String hashFromString(String salt, String password) {
        String generatedPassword = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(salt.getBytes());
            byte[] hashBytes = messageDigest.digest(password.getBytes());

            StringBuilder hexHashBytes = new StringBuilder();
            for (int i = 0; i < hashBytes.length; i++) {
                hexHashBytes.append(Integer.toHexString(i));
            }

            generatedPassword = hexHashBytes.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
