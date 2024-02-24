package ru.modiconme.core.io;

import java.io.*;

public class DataStreamsEx {
    public static void main(String[] args) {
        String path = "src/main/resources/file.bin";
        try (
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(path, true));
                DataInputStream dis = new DataInputStream(new FileInputStream(path))
        ) {
            int value = 100;
            dos.writeInt(value);
            dos.writeBoolean(true);
            dos.writeUTF("Hello");
            int r;
            while ((r = dis.read()) != -1) {
                System.out.println(r);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
