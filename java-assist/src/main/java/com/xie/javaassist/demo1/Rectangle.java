package com.xie.javaassist.demo1;

/**
 * Created by xieyang on 17/5/13.
 */
public class Rectangle {

    private int length;
    private int width;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "Rectangle 修改了";
    }
}
