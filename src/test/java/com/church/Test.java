package com.church;

import java.util.Base64;

public class Test {
    public static void main(String[] args) {
        String encodedKey = "T2uXpqVzIcohxSODVrT63cGPmGXPb2MPPc1IGuncI1Q=";
        byte[] decodedBytes = Base64.getDecoder().decode(encodedKey);
        System.out.println(new String(decodedBytes));
    }
}