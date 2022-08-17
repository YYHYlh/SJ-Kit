package org.sj.decrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Shiro implements DecryptFunc{

    public byte[] decrpytCBC(String key,byte[] encoded) throws Exception {
        try {
//            return new AesCipherService().decrypt(encoded,new Base64().decrypt(key.getBytes())).getBytes();
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(new Base64().decrypt(key.getBytes()), "AES");
            byte[] iv = new byte[16];
            byte[] data = new byte[encoded.length-16];
            System.arraycopy(encoded, 0, iv, 0, 16);
            System.arraycopy(encoded, 16, data, 0, data.length);
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] result = cipher.doFinal(encoded);
            return result;
        }catch (Exception e){
            return new byte[0];
        }
    }

    public byte[] decrpytGCM(String key,byte[] encoded) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            byte[] iv = new byte[16];
            byte[] data = new byte[encoded.length-16];
            System.arraycopy(encoded, 0, iv, 0, 16);
            System.arraycopy(encoded, 16, data, 0, data.length);
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] result = cipher.doFinal(encoded);
            return result;
        }catch (Exception e){
            return new byte[0];
        }
    }

    @Override
    public byte[] decrypt(byte[] encoded) {
        try
        {
            InputStreamReader read = new InputStreamReader(this.getClass().getResourceAsStream("/ShiroKey.txt"));
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            byte[] ret = new byte[0];
            while ((lineTxt = bufferedReader.readLine()) != null)
            {
                ret = decrpytCBC(lineTxt,encoded);
                if (ret.length>0){
                    System.out.println("key:"+lineTxt);
                    System.out.println("CBC");
                    return ret;
                }
                ret = decrpytGCM(lineTxt,encoded);
                if (ret.length>0){
                    System.out.println("key:"+lineTxt);
                    System.out.println("GCM");
                    return ret;
                }
            }
            bufferedReader.close();
            read.close();
        }
        catch (Exception e)
        {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

        return new byte[0];
    }
}
