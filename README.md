# SJ-Kit

# 项目背景

研判过程中，出现了一些解码的需求。简单的解码可以使用在线解码，但是存在以下痛点。

1.  数据保存

2.  数据展示

3.  复杂加密、编码的数据，解密不方便。

针对以上情况，将近两周出现的研判解码需求进行了整合，形成本项目。

# 主要功能

## 解码

内置了如下8种解码方式

1.  Base64

2.  Urldeocde

3.  Hex

4.  Gzip

    部分反序列化漏洞的Payload会使用gzip编码，例如：

    *   帆软报表反序列化漏洞

    *   用友NC反序列化漏洞

5.  Class文件反编译

    基于jdk内置的`javap`，可能会有些问题

6.  Bcel字节码反编译

7.  CAS 加密解码

8.  Shiro RememberMe解码

    默认支持CBC和GCM两种解密方式，内置了100个key，key在`ShiroKey.txt`，可以自行添加或者修改

## 反序列化研判

由于序列化漏洞相比一般漏洞利用过程不够直观，本程序基于常见序列化链的类名进行辅助检测，发现恶意的利用链类名时进行告警。

本程序默认内置了[https://github.com/ikkisoft/SerialKiller](https://github.com/ikkisoft/SerialKiller "https://github.com/ikkisoft/SerialKiller")项目的序列化黑名单类。如需添加新的类，可以在Serial.txt中进行添加，支持正则语法。

# 使用方法

可以直接使用

支持参数：

*   解码类型。支持多层解码。举个例子，如果需要对一段数据使用hex→base64→gzip解码，可以使用如下命令：

    ```java
    java -jar sj-kit.jar hex base64 gzip ABCDEDEFABCDEDEFABCDEDEF....
    ```

*   \-w 解码结果保存到文件中

*   \-s 开启反序列化漏洞研判

*   \-f 解码的数据从文件中读取

举个例子，如下语句，将会对数据进行16进制解码，并将解码结果存在1.ser中，同时在解码后会进行反序列化研判。

```java
java -jar sj-kit.jar -s -w 1.ser hex DATA....
```

# 拓展

本程序仅内置了8种解码方法，如需新增解码方法，需要完成如下两步操作：

1.  只需要在org.sj.decrypt路径下新增一个文件，实现`DecryptFunc`接口，并实现`decrpyt`方法即可。举个例子，实现Base64解码如下：

    ```java
    package org.sj.decrypt;

    public class Base64 implements DecryptFunc {
        @Override
        public  byte[] decrypt(byte[] encoded) {
            return java.util.Base64.getDecoder().decode(encoded);
        }
    }

    ```

2.  在`org.sj.Start.java`文件的`init`函数中注册该方法。注册方法很简单，在函数的return前，新增一行代码。举个例子，注册Base64解码如下，注意方法名需全部小写。

    ```java
    supportList.add("base64");

    ```
