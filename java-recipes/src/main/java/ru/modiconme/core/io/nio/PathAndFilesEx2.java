package ru.modiconme.core.io.nio;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class PathAndFilesEx2 {
    public static void main(String[] args) {
        Path filePath = Paths.get("src/main/resources/file.txt");
        Path filePath2 = Paths.get("file2.txt");
        Path directoryPath = Paths.get("src/main/resources");

        try {
            Files.copy(filePath, directoryPath.resolve(filePath2), StandardCopyOption.REPLACE_EXISTING);
//            Files.move(filePath, directoryPath.resolve("file.txt")); // move and rename
//            Files.delete(directoryPath.resolve(filePath2)); // delete
//            String text = "Sample text";
//            Files.write(filePath, text.getBytes());
            List<String> strings = Files.readAllLines(filePath);
            System.out.println(strings);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class WaltFileTreeEx {
    public static void main(String[] args) {
        Path filePath = Paths.get("src/main/resources/file.txt");
        Path directoryPath = Paths.get("src/main/resources/dir1");
        MyFileVisitor fileVisitor = new MyFileVisitor();
        try {
            Files.walkFileTree(directoryPath, fileVisitor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class MyFileVisitor implements FileVisitor<Path> {
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("Enter to directory: " + dir);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        System.out.println("Visit file: " + file.getFileName());
        System.out.println(Files.readAllLines(file));
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.out.println("Error visit file: " + file.getFileName());
        return FileVisitResult.TERMINATE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        System.out.println("Dir: " + dir + " is visited");
        return FileVisitResult.CONTINUE;
    }
}

class CopyFileTree {
    public static void main(String[] args) {
        Path sourcePath = Paths.get("src/main/resources/dir1");
        Path destinationPath = Paths.get("src/main/resources/copyDir");
        MyFileVisitor2 fileVisitor2 = new MyFileVisitor2(sourcePath, destinationPath);
        MyFileVisitor3 fileVisitor3 = new MyFileVisitor3();
        try {
//            Files.walkFileTree(sourcePath, fileVisitor2); // copy non empty directory
            Files.walkFileTree(destinationPath, fileVisitor3); // delete non empty directory
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

class MyFileVisitor2 extends SimpleFileVisitor<Path> {
    Path source;
    Path destination;

    public MyFileVisitor2(Path source, Path destination) {
        this.source = source;
        this.destination = destination;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        Path newDestination = destination.resolve(source.relativize(dir));
        Files.copy(dir, newDestination, StandardCopyOption.REPLACE_EXISTING);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Path newDestination = destination.resolve(source.relativize(file));
        Files.copy(file, newDestination, StandardCopyOption.REPLACE_EXISTING);
        return FileVisitResult.CONTINUE;
    }
}

class MyFileVisitor3 extends SimpleFileVisitor<Path> {

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        Files.delete(dir);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Files.delete(file);
        return FileVisitResult.CONTINUE;
    }
}