package com.dki.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordEncryptor {
	private static final String SALT_ALGORITHM = "SHA1PRNG";
    private static final int SALT_SIZE_BYTES = 16;

    public static String encryptPassword(String password, String salt) {
        String encryptedPassword = null;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            String passwordWithSalt = password + salt;
            byte[] hashedBytes = messageDigest.digest(passwordWithSalt.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }

            encryptedPassword = stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return encryptedPassword;
    }

    public static String generateSalt() {
        SecureRandom secureRandom;
        try {
            secureRandom = SecureRandom.getInstance(SALT_ALGORITHM);
            byte[] salt = new byte[SALT_SIZE_BYTES];
            secureRandom.nextBytes(salt);
            return bytesToHex(salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public static boolean verifyPassword(String rawPassword, String salt, String storedPassword) {
        String hashedPasswordToCheck = encryptPassword(rawPassword, salt);
        return hashedPasswordToCheck != null && hashedPasswordToCheck.equals(storedPassword);
    }
    
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b & 0xff));
        }
        return hexString.toString();
    }
}
