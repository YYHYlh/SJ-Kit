package org.sj.decrypt;

import org.apereo.spring.webflow.plugin.EncryptedTranscoder;
import org.cryptacular.bean.CipherBean;
import org.sj.utils.Reflections;

import java.io.FileOutputStream;
import java.util.Base64;

public class Cas implements DecryptFunc{
    private byte[] decode(String str){
        byte[] code =  Base64.getDecoder().decode(str);
        try {
            EncryptedTranscoder et = new EncryptedTranscoder();
            CipherBean cipherBean = (CipherBean) Reflections.getFieldValue(et, "cipherBean");
            byte[] ser = cipherBean.decrypt(code);
            return ser;
        }catch (Exception e){
            return new byte[0];
        }
    }

    @Override
    public byte[] decrypt(byte[] encoded) {
        return decode(new String(encoded));
    }
}
