package com.psych.mtktool.mama.a.a;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* loaded from: classes.dex */
public class b {

    /* renamed from: c.a.a.b$b, reason: collision with other inner class name */
    private static class C0051b {

        /* renamed from: a, reason: collision with root package name */
        int f1486a;

        /* renamed from: b, reason: collision with root package name */
        int f1487b;

        /* renamed from: c, reason: collision with root package name */
        String f1488c;

        private C0051b() {
        }
    }

    public static void a(String str) throws IOException {
        long j;
        ZipFile zipFile = new ZipFile(str);
        ArrayList arrayList = new ArrayList();
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        long j2 = 0;
        while (entries.hasMoreElements()) {
            ZipEntry nextElement = entries.nextElement();
            long length = j2 + nextElement.getName().length() + 30;
            if (nextElement.isDirectory()) {
                j = 0;
            } else {
                j = nextElement.getCompressedSize();
                C0051b c0051b = new C0051b();
                c0051b.f1488c = nextElement.getName();
                c0051b.f1486a = (int) j;
                c0051b.f1487b = (int) length;
                arrayList.add(c0051b);
                System.out.println(nextElement.getName() + " " + length);
            }
            j2 = length + j;
        }
        FileOutputStream fileOutputStream = new FileOutputStream(str + ".idx");
        ByteBuffer allocate = ByteBuffer.allocate(4);
        ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
        ByteBuffer order = allocate.order(byteOrder);
        order.putInt(arrayList.size());
        fileOutputStream.write(order.array());
        ByteBuffer order2 = ByteBuffer.allocate(10).order(byteOrder);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            C0051b c0051b2 = (C0051b) it.next();
            order2.rewind();
            order2.putInt(c0051b2.f1487b);
            order2.putInt(c0051b2.f1486a);
            order2.putShort((short) c0051b2.f1488c.length());
            fileOutputStream.write(order2.array());
            byte[] bArr = new byte[c0051b2.f1488c.length()];
            byte[] bytes = c0051b2.f1488c.getBytes("ISO-8859-1");
            System.arraycopy(bytes, 0, bArr, 0, bytes.length);
            fileOutputStream.write(bArr);
        }
        fileOutputStream.close();
    }
}
