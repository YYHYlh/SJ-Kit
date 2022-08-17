package org.sj.decrypt;

public class Base64 implements DecryptFunc {
    @Override
    public  byte[] decrypt(byte[] encoded) {
        return java.util.Base64.getDecoder().decode(encoded);
    }
}
