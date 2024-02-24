package ru.modiconme.core.io;

import java.io.*;
import java.util.Arrays;

public class FileIOStreamsEx {
    static File file1 = new File("src/main/resources/img1.png");
    static File file2 = new File("src/main/resources/img2.png");

    public static void main(String[] args) {
        try(
                FileInputStream fis = new FileInputStream(file1);
                FileOutputStream fos = new FileOutputStream(file2)
        ) {
            byte[] bytes = fis.readAllBytes();
            int c;
//            while ((c = fis.read()) != -1) {
//                System.out.println(c);
//            }

            fos.write(bytes);
            System.out.println(Arrays.toString(bytes));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class BufferedIOStreamsEx {
    public static void main(String[] args) {
        try(
                BufferedInputStream bfis = new BufferedInputStream(new FileInputStream(FileIOStreamsEx.file1));
                BufferedOutputStream bfos = new BufferedOutputStream(new FileOutputStream(FileIOStreamsEx.file2))
        ) {
            byte[] bytes = bfis.readAllBytes();
            bfos.write(bytes);
            System.out.println(Arrays.toString(bytes));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
