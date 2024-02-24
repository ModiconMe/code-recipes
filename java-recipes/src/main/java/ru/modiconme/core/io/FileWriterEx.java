package ru.modiconme.core.io;

import java.io.*;

import static ru.modiconme.core.io.FileWriterEx.*;
import static ru.modiconme.core.io.FileWriterEx.file;
import static ru.modiconme.core.io.FileWriterEx.text;

public class FileWriterEx {
    static String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n" +
            "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\n" +
            "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\n" +
            "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n";
    static File file = new File("src/main/resources/file.txt");
    static File file1 = new File("src/main/resources/file1.txt");

    public static void main(String[] args) {
        try (
                FileWriter fw = new FileWriter(file, true);
                FileReader fr = new FileReader(file)
        ) {
            fw.write(text);
            int c;
            while ((c = fr.read()) != -1) {
                System.out.print((char)c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class BufferRWEx {
    public static void main(String[] args) {
        try (
                BufferedReader br = new BufferedReader(new FileReader(file));
                BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))
        ) {
            bw.write(text);
            String c;
            while ((c = br.readLine()) != null) {
                System.out.print(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
