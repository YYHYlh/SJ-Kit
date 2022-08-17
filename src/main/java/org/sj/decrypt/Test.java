package org.sj.decrypt;


import static org.sj.utils.GetContent.getContent;

public class Test {
    public static void print(byte[] encoded,String method) throws Exception{
        String className = "org.sj.decrypt." + method;
        try {
            byte[] decoded = ((DecryptFunc) Class.forName(className).newInstance()).decrypt(encoded);
            System.out.println(new String(decoded));
        }catch (java.lang.ClassNotFoundException e){
            System.out.println("没有找到类:" + className );
        }
    }



    public static void main(String[] args) throws Exception{
        byte[] base64 = "dGVzdEJhc2U2NA==".getBytes();
        print(base64,"Base64");
//        print(bcel,"Bcel");
//        print(cas,"Cas");
//        print(classFile,"ClassFile");
//        print(new Hex().decrypt(gzip);,"Gzip");
//        print(new Base64().decrypt(shiro),"Shiro");
        // print(hex,"Hex");
        // print(url,"Url");



    }
}
