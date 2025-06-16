package com.psych.mtktool.utils;

public class ImgEntry {
    int offset;
    int size;
    String name;

    public ImgEntry(int offset, int size, String name) {
        this.offset = offset;
        this.size = size;
        this.name = name;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}