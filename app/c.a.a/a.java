package com.psych.mtktool.mama.a.a;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/* loaded from: classes.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private boolean f1479a;

    /* renamed from: b, reason: collision with root package name */
    private String f1480b;

    /* renamed from: c, reason: collision with root package name */
    private String f1481c;

    /* renamed from: d, reason: collision with root package name */
    private ArrayList<b> f1482d;

    public class b {

        /* renamed from: a, reason: collision with root package name */
        int f1483a;

        /* renamed from: b, reason: collision with root package name */
        int f1484b;

        /* renamed from: c, reason: collision with root package name */
        String f1485c;

        public String toString() {
            return "FileInfo{offset=" + this.f1483a + ", size=" + this.f1484b + ", name='" + this.f1485c + "'}";
        }

        private b(a aVar) {
        }
    }

    public interface c {
        void a(int i, int i2);
    }

    public a(String str, String str2) {
        this.f1479a = false;
        this.f1480b = str;
        this.f1481c = str2;
        try {
            this.f1479a = m(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<b> g() {
        long length;
        FileInputStream fileInputStream;
        if (this.f1482d == null) {
            this.f1482d = new ArrayList<>();
            if (this.f1479a) {
                fileInputStream = new FileInputStream(new File(this.f1480b));
                fileInputStream.skip(4L);
                ByteBuffer order = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
                fileInputStream.read(order.array());
                length = order.getInt();
            } else {
                File file = new File(this.f1481c);
                length = file.length() / 32;
                fileInputStream = new FileInputStream(file);
            }
            System.out.println(length);
            ByteBuffer order2 = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
            byte[] bArr = new byte[24];
            for (int i = 0; i < length; i++) {
                b bVar = new b();
                order2.rewind();
                fileInputStream.read(order2.array());
                bVar.f1483a = order2.getInt();
                bVar.f1484b = order2.getInt();
                fileInputStream.read(bArr);
                bVar.f1485c = new String(bArr, "ISO-8859-1");
                this.f1482d.add(bVar);
            }
            fileInputStream.close();
        }
        return this.f1482d;
    }

    private b j() {
        ArrayList<b> arrayList = this.f1482d;
        if (arrayList == null || arrayList.size() == 0) {
            return null;
        }
        b bVar = this.f1482d.get(0);
        Iterator<b> it = this.f1482d.iterator();
        while (it.hasNext()) {
            b next = it.next();
            if (next.f1483a < bVar.f1483a) {
                bVar = next;
            }
        }
        return bVar;
    }

    private int k() {
        return (this.f1482d.size() * 32) + 8;
    }

    public static boolean m(String str) {
        FileInputStream fileInputStream = new FileInputStream(new File(str));
        byte[] bArr = new byte[4];
        fileInputStream.read(bArr);
        fileInputStream.close();
        return new String(bArr).equals("VER2");
    }

    private void n(b bVar) {
        File file = new File(this.f1480b);
        byte[] bArr = new byte[bVar.f1484b * 2048];
        FileInputStream fileInputStream = new FileInputStream(file);
        fileInputStream.skip(bVar.f1483a * 2048);
        fileInputStream.read(bArr);
        fileInputStream.close();
        bVar.f1483a = (int) (file.length() / 2048);
        FileOutputStream fileOutputStream = new FileOutputStream(this.f1480b, true);
        fileOutputStream.write(bArr);
        fileOutputStream.close();
    }

    public static String o(String str) {
        int indexOf = str.indexOf(0);
        return indexOf > 0 ? str.substring(0, indexOf) : str;
    }

    private void s() {
        if (!this.f1479a) {
            FileOutputStream fileOutputStream = new FileOutputStream(this.f1481c);
            ByteBuffer order = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
            Iterator<b> it = this.f1482d.iterator();
            while (it.hasNext()) {
                b next = it.next();
                order.rewind();
                order.putInt(next.f1483a);
                order.putInt(next.f1484b);
                fileOutputStream.write(order.array());
                byte[] bArr = new byte[24];
                byte[] bytes = next.f1485c.getBytes("ISO-8859-1");
                System.arraycopy(bytes, 0, bArr, 0, bytes.length);
                fileOutputStream.write(bArr);
            }
            fileOutputStream.close();
            return;
        }
        int size = (this.f1482d.size() * 32) + 8;
        System.out.println("writeDirFile");
        System.out.println(size);
        b j = j();
        if (j != null) {
            System.out.println(j.f1483a * 2048);
            if (j.f1483a * 2048 < size) {
                System.out.println("need move");
                n(j);
            }
        }
        ByteBuffer allocate = ByteBuffer.allocate(4);
        ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
        ByteBuffer order2 = allocate.order(byteOrder);
        order2.putInt(this.f1482d.size());
        RandomAccessFile randomAccessFile = new RandomAccessFile(this.f1480b, "rw");
        randomAccessFile.seek(4L);
        randomAccessFile.write(order2.array());
        ByteBuffer order3 = ByteBuffer.allocate(8).order(byteOrder);
        Iterator<b> it2 = this.f1482d.iterator();
        while (it2.hasNext()) {
            b next2 = it2.next();
            order3.rewind();
            order3.putInt(next2.f1483a);
            order3.putInt(next2.f1484b);
            randomAccessFile.write(order3.array());
            byte[] bArr2 = new byte[24];
            byte[] bytes2 = next2.f1485c.getBytes("ISO-8859-1");
            System.arraycopy(bytes2, 0, bArr2, 0, bytes2.length);
            randomAccessFile.write(bArr2);
        }
        randomAccessFile.close();
    }

    public synchronized void a(String str, String str2) {
        File file = new File(str);
        if (str2 == null) {
            str2 = file.getName();
        }
        if (h().indexOf(file.getName()) != -1) {
            throw new IllegalArgumentException(str2);
        }
        b bVar = new b();
        bVar.f1485c = str2;
        long length = file.length();
        int i = (int) (length / 2048);
        if (length % 2048 != 0) {
            i++;
        }
        bVar.f1484b = i;
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bArr = new byte[2048];
        bVar.f1483a = (int) (new File(this.f1480b).length() / 2048);
        FileOutputStream fileOutputStream = new FileOutputStream(this.f1480b, true);
        for (int i2 = 0; i2 < bVar.f1484b; i2++) {
            int read = fileInputStream.read(bArr);
            if (read > -1) {
                if (read < 2048) {
                    Arrays.fill(bArr, read, 2047, (byte) 0);
                }
                fileOutputStream.write(bArr, 0, 2048);
            }
        }
        fileInputStream.close();
        fileOutputStream.close();
        this.f1482d.add(bVar);
        s();
    }

    public boolean b() {
        return new File(this.f1480b).getFreeSpace() > ((long) f());
    }

    public synchronized void c(String str) {
        this.f1482d.remove(this.f1482d.get(h().indexOf(str)));
        s();
    }

    public synchronized void d(String[] strArr) {
        ArrayList<String> h = h();
        int length = strArr.length;
        b[] bVarArr = new b[length];
        for (int i = 0; i < strArr.length; i++) {
            bVarArr[i] = this.f1482d.get(h.indexOf(strArr[i]));
        }
        for (int i2 = 0; i2 < length; i2++) {
            this.f1482d.remove(bVarArr[i2]);
        }
        s();
    }

    public synchronized void e(String str) {
        b bVar = this.f1482d.get(h().indexOf(str));
        File file = new File(this.f1480b);
        byte[] bArr = new byte[2048];
        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream(new File(file.getParentFile(), o(bVar.f1485c)));
        fileInputStream.skip(bVar.f1483a * 2048);
        for (int i = 0; i < bVar.f1484b; i++) {
            fileInputStream.read(bArr);
            fileOutputStream.write(bArr);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }

    public synchronized int f() {
        int i;
        i = 0;
        Iterator<b> it = this.f1482d.iterator();
        while (it.hasNext()) {
            i += it.next().f1484b * 2048;
        }
        if (this.f1479a) {
            i += k();
        }
        return i;
    }

    public synchronized ArrayList<String> h() {
        ArrayList<String> arrayList;
        arrayList = new ArrayList<>();
        Iterator<b> it = g().iterator();
        while (it.hasNext()) {
            arrayList.add(o(it.next().f1485c));
        }
        return arrayList;
    }

    public int i() {
        return this.f1482d.size();
    }

    public String l() {
        return new File(this.f1480b).getName();
    }

    public synchronized void p(c cVar) {
        int i;
        int i2;
        File file = new File(this.f1480b);
        File file2 = new File(this.f1480b + "_tmp");
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        if (this.f1479a) {
            i = (k() / 2048) + 1 + 0;
            fileOutputStream.write("VER2".getBytes("ISO-8859-1"));
            fileOutputStream.write(new byte[(i * 2048) - 4]);
        } else {
            i = 0;
        }
        Iterator<b> it = this.f1482d.iterator();
        int i3 = 0;
        while (it.hasNext()) {
            int i4 = it.next().f1484b;
            if (i4 > i3) {
                i3 = i4;
            }
        }
        byte[] bArr = new byte[2048];
        int size = this.f1482d.size();
        for (int i5 = 0; i5 < size; i5++) {
            b bVar = this.f1482d.get(i5);
            randomAccessFile.seek(bVar.f1483a * 2048);
            int i6 = 0;
            while (true) {
                i2 = bVar.f1484b;
                if (i6 >= i2) {
                    break;
                }
                randomAccessFile.read(bArr, 0, 2048);
                fileOutputStream.write(bArr, 0, 2048);
                i6++;
            }
            bVar.f1483a = i;
            i += i2;
            if (cVar != null) {
                cVar.a(i5, size);
            }
        }
        fileOutputStream.close();
        randomAccessFile.close();
        if (!file.delete()) {
            file2.delete();
            throw new IOException("Cannot delete old file");
        }
        file2.renameTo(file);
        s();
    }

    public synchronized void q(String str, String str2) {
        this.f1482d.get(h().indexOf(str)).f1485c = str2;
        s();
    }

    public synchronized void r(String str, String str2) {
        b bVar = this.f1482d.get(h().indexOf(str));
        File file = new File(str2);
        long length = file.length();
        int i = (int) (length / 2048);
        if (length > bVar.f1484b * 2048) {
            System.out.println("write to end");
            if (length % 2048 != 0) {
                i++;
            }
            bVar.f1484b = i;
            FileInputStream fileInputStream = new FileInputStream(file);
            bVar.f1483a = (int) (new File(this.f1480b).length() / 2048);
            FileOutputStream fileOutputStream = new FileOutputStream(this.f1480b, true);
            byte[] bArr = new byte[2048];
            for (int i2 = 0; i2 < bVar.f1484b; i2++) {
                int read = fileInputStream.read(bArr);
                if (read > -1) {
                    if (read < 2048) {
                        Arrays.fill(bArr, read, 2047, (byte) 0);
                    }
                    fileOutputStream.write(bArr, 0, 2048);
                }
            }
            fileInputStream.close();
            fileOutputStream.close();
        } else {
            System.out.println("real replace");
            if (length % 2048 != 0) {
                i++;
            }
            bVar.f1484b = i;
            FileInputStream fileInputStream2 = new FileInputStream(str2);
            byte[] bArr2 = new byte[2048];
            RandomAccessFile randomAccessFile = new RandomAccessFile(this.f1480b, "rwd");
            randomAccessFile.seek(bVar.f1483a * 2048);
            for (int i3 = 0; i3 < bVar.f1484b; i3++) {
                int read2 = fileInputStream2.read(bArr2);
                if (read2 > -1) {
                    if (read2 < 2048) {
                        Arrays.fill(bArr2, read2, 2047, (byte) 0);
                    }
                    randomAccessFile.write(bArr2, 0, 2048);
                }
            }
            fileInputStream2.close();
            randomAccessFile.close();
        }
        s();
    }
}