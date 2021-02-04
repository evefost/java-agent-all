package com.xie.bytebuddy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Class2FileUtils {

    public static void outputClazz(byte[] bytes) {

        FileOutputStream out = null;
        try {
            String pathName = Class2FileUtils.class.getResource("/").getPath() + "ByteBuddyHelloWorld.class";
            out = new FileOutputStream(new File(pathName));
            System.out.println("类输出路径：" + pathName);
            out.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != out) try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
