package com.TMT.TMT_BE_PaymentServer.global.common.token;

import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SendHashverification {
    public static String generateHmac(String data, String key) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "SHA256");
        mac.init(secretKeySpec);
        byte[] rawHmac = mac.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(rawHmac);
    }

    public static boolean verifyHmac(String data, String receivedHmac, String key) throws Exception {
        String generatedHmac = generateHmac(data, key);
        return generatedHmac.equals(receivedHmac);
    }
}