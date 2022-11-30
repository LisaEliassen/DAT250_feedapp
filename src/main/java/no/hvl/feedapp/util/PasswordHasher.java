package no.hvl.feedapp.util;

import com.lambdaworks.crypto.SCryptUtil;

public class PasswordHasher {

    public String hashPassword(String passwordString) {
        String hash = SCryptUtil.scrypt(passwordString, (int) Math.pow(2, 14), 8, 1);
        return hash;
    }

    public boolean checkPassword(String inputPassword, String hash) {
        if (inputPassword.length() > 0 && inputPassword != null) {
            return SCryptUtil.check(inputPassword, hash);
        }
        return false;
    }
}
