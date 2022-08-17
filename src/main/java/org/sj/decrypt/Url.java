package org.sj.decrypt;

import java.net.URLDecoder;

public class Url implements DecryptFunc{
    @Override
    public byte[] decrypt(byte[] encoded) {
        return URLDecoder.decode(new String(encoded)).getBytes();
    }
}
