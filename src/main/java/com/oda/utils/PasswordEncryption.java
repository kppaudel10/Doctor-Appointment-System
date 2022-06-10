package com.oda.utils;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class PasswordEncryption {
    private static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        /* MessageDigest instance for hashing using SHA256 */
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        /* digest() method called to calculate message digest of an input and return array of byte */
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    private static String toHexString(byte[] hash)
    {
        /* Convert byte array of hash into digest */
        BigInteger number = new BigInteger(1, hash);

        /* Convert the digest into hex value */
        StringBuilder hexString = new StringBuilder(number.toString(16));

        /* Pad with leading zeros */
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    public String getEncryptedPassword(String password){
        try {
            return toHexString(getSHA(password));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String password = "Doctor@123";
        PasswordEncryption passwordEncryption = new PasswordEncryption();
        System.out.println(passwordEncryption.getEncryptedPassword(password));
//
//        String encryptPassword = "c519e76f6719da3d089433d16ec68662b80087361af1253b0f1ca12fa4da9781";
//
//        if (passwordEncryption.getEncryptedPassword(password).equalsIgnoreCase(encryptPassword)){
//            System.out.println("Match");
//        }
    }
}
