package org.sj.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SerialKill {
    public void scan(String data) throws Exception{
        InputStreamReader read = new InputStreamReader(this.getClass().getResourceAsStream("/Serial.txt"));
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        byte[] ret = new byte[0];
        while ((lineTxt = bufferedReader.readLine()) != null)
        {
            try{
            if (Pattern.compile(lineTxt).matcher(data).find()) {
                System.out.println("发现存在恶意的序列化类:" + lineTxt.replace("\\",""));
            }}catch (Exception e){
                System.out.println(lineTxt);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(       "asfasjava.util.PriorityQueue.asfas123".matches("java\\.util\\.PriorityQueue"));
    }

}
