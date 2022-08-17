package org.sj.decrypt;

import com.sun.org.apache.bcel.internal.classfile.Utility;

public class Bcel implements DecryptFunc{
    @Override
    public byte[] decrypt(byte[] encoded) {
        try {
            String bcel = new String(encoded);
            int index = bcel.indexOf("$$BCEL$$");
            if (index < 0) {
                return new byte[0];
            }
            String str = bcel.substring(index + 8);
            byte[] buf = Utility.decode(str, true);
            return buf;
        }catch (Exception e){
            return new byte[0];
        }
    }

}
