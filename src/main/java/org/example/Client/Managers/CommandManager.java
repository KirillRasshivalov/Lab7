package org.example.Client.Managers;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CommandManager implements Serializable {
    private static String passwordWithHash;

    public String getHash(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");;
        byte[] hashedPassword = md.digest(password.getBytes("UTF-8"));
        passwordWithHash = Base64.getEncoder().encodeToString(hashedPassword);
        return passwordWithHash;
    }
}
