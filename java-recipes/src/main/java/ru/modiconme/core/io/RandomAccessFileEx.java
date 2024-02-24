package ru.modiconme.core.io;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileEx {
    public static void main(String[] args) {
        try (RandomAccessFile raf = new RandomAccessFile("src/main/resources/file1.txt", "rw")) {
            String c;
            raf.seek(10); // change cursor position
            while ((c = raf.readLine()) != null) {
                System.out.print(c);
            }
            raf.writeUTF("Hello");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
