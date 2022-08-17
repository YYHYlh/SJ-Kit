package org.sj.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CmdExec {
    public static String exec(String cmd) throws Exception{
        Process p = null;
        p = new ProcessBuilder(cmd.split(" ")).start();
        byte[] b = new byte[1024];
        int readbytes = -1;
        StringBuffer sb = new StringBuffer();
        // 读取进程输出值
        InputStream in = p.getInputStream();
        try {
            while ((readbytes = in.read(b)) != -1) {
                sb.append(new String(b, 0, readbytes));
            }
        } catch (IOException e1) {
        } finally {
            try {
                in.close();
            } catch (IOException e2) {
            }
        }
        return sb.toString();
    }
}
