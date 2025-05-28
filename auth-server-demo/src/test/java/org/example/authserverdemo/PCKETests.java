package org.example.authserverdemo;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PCKETests {

    @Test
    void createCodeChallenge() throws NoSuchAlgorithmException {
        String codeVerifier = "abc";
        String codeChallengeMethod = "SHA-256";

        MessageDigest md = MessageDigest.getInstance(codeChallengeMethod);
        byte[] digest = md.digest(codeVerifier.getBytes(StandardCharsets.US_ASCII));

        String  codeChallenge = Base64
                                    .getUrlEncoder()
                                    .withoutPadding()
                                    .encodeToString(digest);

        System.out.println("== Code Challenge ==");
        System.out.println(codeChallenge);
    }
}
