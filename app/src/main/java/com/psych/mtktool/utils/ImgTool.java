package com.psych.mtktool.utils;

import java.io.*;
import java.nio.*;
import java.nio.charset.*;
import java.util.*;

public class ImgTool {

    public static class ImgEntryData {
        public String name;
        public int offset;
        public int size;

        public ImgEntryData(String name, int offset, int size) {
            this.name = name;
            this.offset = offset;
            this.size = size;
        }
    }

    public static List<ImgEntryData> readImg(File file) throws IOException {
        List<ImgEntryData> entries = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            byte[] magicBytes = new byte[4];
            raf.readFully(magicBytes);
            String magic = new String(magicBytes, "ISO-8859-1");
            if (!magic.equals("VER2")) throw new IOException("Invalid IMG format");

            int fileCount = Integer.reverseBytes(raf.readInt());
            for (int i = 0; i < fileCount; i++) {
                int offset = Integer.reverseBytes(raf.readInt());
                int size = Integer.reverseBytes(raf.readInt());
                byte[] nameBytes = new byte[24];
                raf.readFully(nameBytes);
                String name = new String(nameBytes, StandardCharsets.ISO_8859_1).trim();
                entries.add(new ImgEntryData(name, offset, size));
            }
        }
        return entries;
    }

    public static void replaceFile(File imgFile, String fileName, byte[] newData) throws IOException {
        List<ImgEntryData> entries = readImg(imgFile);
        Map<String, byte[]> files = extractAll(imgFile, entries);

        if (!files.containsKey(fileName)) throw new IOException("File not found!");

        files.put(fileName, newData);
        rebuildImg(imgFile, files);
    }

    public static void deleteFile(File imgFile, String fileName) throws IOException {
        List<ImgEntryData> entries = readImg(imgFile);
        Map<String, byte[]> files = extractAll(imgFile, entries);

        if (!files.containsKey(fileName)) throw new IOException("File not found!");

        files.remove(fileName);
        rebuildImg(imgFile, files);
    }

    public static void addFile(File imgFile, String fileName, byte[] data) throws IOException {
        List<ImgEntryData> entries = readImg(imgFile);
        Map<String, byte[]> files = extractAll(imgFile, entries);

        if (files.containsKey(fileName)) throw new IOException("File already exists!");

        files.put(fileName, data);
        rebuildImg(imgFile, files);
    }

    private static Map<String, byte[]> extractAll(File imgFile, List<ImgEntryData> entries) throws IOException {
        Map<String, byte[]> files = new LinkedHashMap<>();
        try (RandomAccessFile raf = new RandomAccessFile(imgFile, "r")) {
            for (ImgEntryData e : entries) {
                byte[] data = new byte[e.size];
                raf.seek(e.offset);
                raf.readFully(data);
                files.put(e.name, data);
            }
        }
        return files;
    }

    private static void rebuildImg(File imgFile, Map<String, byte[]> files) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(imgFile, "rw")) {
            raf.setLength(0);
            raf.write("VER2".getBytes("ISO-8859-1"));
            raf.write(Integer.reverseBytes(files.size()));

            int headerSize = 4 + 4 + files.size() * (4 + 4 + 24);
            int currentOffset = headerSize;

            // prepare header
            List<byte[]> dataList = new ArrayList<>();
            for (Map.Entry<String, byte[]> entry : files.entrySet()) {
                String name = entry.getKey();
                byte[] data = entry.getValue();
                raf.write(Integer.reverseBytes(currentOffset));
                raf.write(Integer.reverseBytes(data.length));
                byte[] nameBytes = new byte[24];
                byte[] nameSrc = name.getBytes(StandardCharsets.ISO_8859_1);
                System.arraycopy(nameSrc, 0, nameBytes, 0, Math.min(24, nameSrc.length));
                raf.write(nameBytes);
                dataList.add(data);
                currentOffset += data.length;
            }

            // write data
            for (byte[] data : dataList) {
                raf.write(data);
            }
        }
    }
}
