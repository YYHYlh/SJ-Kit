package org.sj.decrypt;

import org.sj.utils.CmdExec;
import java.io.File;
import java.io.FileOutputStream;

public class ClassFile implements DecryptFunc{
    @Override
    public byte[] decrypt(byte[] encoded){
        try {
            File temp = File.createTempFile("test", ".class");
            System.out.println("文件路径: " + temp.getAbsolutePath());
            temp.deleteOnExit();
            FileOutputStream fop = new FileOutputStream(temp);
            fop.write(encoded);
            fop.flush();
            fop.close();
            String cmd = "javap "+temp.getAbsolutePath();
            String content = CmdExec.exec(cmd);
            return content.getBytes();
        }catch (Exception e){
            return new byte[0];
        }
    }
}
