package com.example.curve_key.utils;

import org.whispersystems.curve25519.Curve25519;
import org.whispersystems.curve25519.Curve25519KeyPair;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class GeneratorKey {
    public static final String PRIMARYKEY = "PrivateKey";
    public static final String PUBLICKEY = "PublicKey";
    public static final String SHAREDSECRET = "SharedSecret";

    public Map<String, String> getKeyPair() {
        Map<String, String> maps = new HashMap<>();
        Curve25519 cipher = Curve25519.getInstance(Curve25519.BEST);
        Curve25519KeyPair keyPair = cipher.generateKeyPair();
        byte[] privateKey = keyPair.getPrivateKey();
        maps.put(PRIMARYKEY, Base64.getEncoder().encodeToString(privateKey));
        byte[] publicKey = keyPair.getPublicKey();
        maps.put(PUBLICKEY, Base64.getEncoder().encodeToString(publicKey));
        byte[] sharedSecret = cipher.calculateAgreement(publicKey, privateKey);
        maps.put(SHAREDSECRET, Base64.getEncoder().encodeToString(sharedSecret));
        return maps;
    }


}
