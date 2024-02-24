package ru.modiconme.core.io;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class FileEx {
    public static void main(String[] args) {
        File file = new File("src/main/resources/file.txt");
        File newFile = new File("src/main/resources/newFile.txt");
        File folder = new File("src/main/resources");

        System.out.println("File absolute path: " + file.getAbsolutePath());
        System.out.println("Folder absolute path: " + folder.getAbsolutePath());
        System.out.println("----------");

        System.out.println("File isAbsolute(): " + file.isAbsolute());
        System.out.println("Folder isAbsolute(): " + folder.isAbsolute());
        System.out.println("----------");

        System.out.println("File isDirectory(): " + file.isDirectory());
        System.out.println("Folder isDirectory(): " + folder.isDirectory());
        System.out.println("----------");

        System.out.println("File isExists: " + file.exists());
        System.out.println("Folder isExists: " + folder.exists());
        System.out.println("----------");

        try {
            System.out.println("File create: " + newFile.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Folder create: " + folder.mkdir());
        System.out.println("----------");

        System.out.println("File size: " + file.length());
        System.out.println("Folder size: " + folder.length());
        System.out.println("----------");

        System.out.println("File delete: " + newFile.delete());
//        System.out.println("Folder delete: " + newFile.delete());
        System.out.println("----------");

        System.out.println("List files: " + Arrays.toString(file.listFiles()));
        System.out.println("List files: " + Arrays.toString(folder.listFiles()));
        System.out.println("----------");

        System.out.println("File isHidden(): " + file.isHidden());
        System.out.println("File canWrite(): " + file.canWrite());
        System.out.println("File canRead(): " + file.canRead());
        System.out.println("File canExecute(): " + file.canExecute());
    }
}
