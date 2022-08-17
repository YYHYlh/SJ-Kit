package org.sj;


import org.sj.decrypt.DecryptFunc;
import org.sj.utils.GetContent;
import org.sj.utils.Hexdump;
import org.sj.utils.SerialKill;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class Start {

    public static HashSet init(){
        HashSet<String> supportList= new HashSet<>();
        supportList.add("base64");
        supportList.add("bcel");
        supportList.add("cas");
        supportList.add("classfile");
        supportList.add("gzip");
        supportList.add("hex");
        supportList.add("shiro");
        supportList.add("url");
        return supportList;
    }

    public static void main(String[] args) throws Exception{
        if (args.length < 2){
            System.out.println("Usage:\njava -jar SJ-Kit.jar args...\n\t-w 结果保存到文件\n\t-d 解码结果用hexdump打印\n\t-s 开启反序列化研判\n\t-f 解码的数据从文件中读取\n\te.g.:\n\t java -jar SJ-Kit.jar base64 YXNmYXNqYXZhLnV0aWwuUHJpb3JpdHlRdWV1ZS5hc2ZhczEyMw==");
            System.exit(0);
        }
        HashSet supportList = init();
        String saveFilePath = "";
        String dataFilePath = "";
        byte[] data=null;
        boolean save2File = false;
        boolean dataFromFile = false;
        boolean scan = false;
        boolean hexdump = false;
        List<String> decryptList = new ArrayList<>();

        for (int i = 0; i < args.length; i++){
            String now = args[i];
            if (now.equals("-w")){
                save2File = true;
                saveFilePath = args[i+1];
            }else if(now.equals("-s")){
                scan = true;
            }else if (now.equals("-d")){
                hexdump = true;

            }else if (now.equals("-f")){
                dataFromFile = true;
                dataFilePath = args[i+1];
            }
            else if (supportList.contains(now.toLowerCase())){
                decryptList.add(now.substring(0,1).toUpperCase()+now.substring(1).toLowerCase());
            }
        }
        if (dataFromFile){
            data = GetContent.getContent(dataFilePath);
        }else {
            data = args[args.length - 1].getBytes();
        }
        for(String method:decryptList){
            String className = "org.sj.decrypt." + method;
            data = ((DecryptFunc) Class.forName(className).newInstance()).decrypt(data);
        }
        if (save2File){
            File file = new File(saveFilePath);
            if (file.exists()){
                file.delete();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data);
        }else if(hexdump){
            System.out.println(Hexdump.dumpHexString(data));
        }else{
            System.out.println(new String(data));
        }
        if (scan){
            new SerialKill().scan(new String(data));
        }

    }
}
