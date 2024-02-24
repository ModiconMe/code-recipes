package ru.modiconme.core.io.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathAndFilesEx {
    public static void main(String[] args) throws IOException {
        Path filePath = Paths.get("src/main/resources/file.txt");
        Path directoryPath = Paths.get("src/main/resources");

        System.out.println("filePath.getFileName(): " + filePath.getFileName());
        System.out.println("directoryPath.getFileName(): " + directoryPath.getFileName());
        System.out.println("--------------------");

        System.out.println("filePath.getParent(): " + filePath.getParent());
        System.out.println("directoryPath.getParent(): " + directoryPath.getParent());
        System.out.println("--------------------");

        System.out.println("filePath.getRoot(): " + filePath.getRoot());
        System.out.println("directoryPath.getRoot(): " + directoryPath.getRoot());
        System.out.println("--------------------");

        System.out.println("filePath.isAbsolute(): " + filePath.isAbsolute());
        System.out.println("directoryPath.isAbsolute(): " + directoryPath.isAbsolute());
        System.out.println("--------------------");

        System.out.println("filePath.toAbsolutePath(): " + filePath.toAbsolutePath());
        System.out.println("directoryPath.toAbsolutePath(): " + directoryPath.toAbsolutePath());
        System.out.println("--------------------");

        System.out.println("directoryPath.resolve(filePath): " + directoryPath.resolve(filePath)); // add filePath to directoryPath
        System.out.println("--------------------");

        System.out.println("directoryPath.relativize(filePath): " + directoryPath.relativize(filePath)); // return relative path directoryPath and filePath
        System.out.println("--------------------");

        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
        if (!Files.exists(directoryPath)) {
            Files.createDirectory(directoryPath);
        }

        System.out.println("Files.isReadable(filePath): " + Files.isReadable(filePath));
        System.out.println("Files.isWritable(filePath): " + Files.isWritable(filePath));
        System.out.println("Files.isExecutable(filePath): " + Files.isExecutable(filePath));
        System.out.println("--------------------");

        System.out.println("Files.isSameFile(filePath, directoryPath): " + Files.isSameFile(filePath, directoryPath));
        System.out.println("--------------------");

        System.out.println("Files.size(filePath): " + Files.size(filePath));
        System.out.println("--------------------");

        System.out.println("Files.getAttribute(filePath, \"creationTime\"): " + Files.getAttribute(filePath, "creationTime"));
        System.out.println("Files.readAttributes(filePath, \"*\"): " + Files.readAttributes(filePath, "*")); // return all attributes
        System.out.println("--------------------");

    }
}
