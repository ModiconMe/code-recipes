package ru.modiconme.recepies.io;

import lombok.SneakyThrows;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class FileRecipes {

    @SneakyThrows
    public static boolean readFile() {
//        File resourceFromRepositoryRoot = new File("java-recepies/build/resources/main/config.properties");
//        File resourceFromModuleRoot = new File("resources/config.properties");

        // 1. Получаем абсолютный путь до ресурса через класс лоадер
        // указываем относительный путь от папки resources, в jar работает только getResourceAsStream
        // + хорошо подходит для тестов
        ClassLoader classLoader1 = Thread.currentThread().getContextClassLoader();
        ClassLoader classLoader2 = FileRecipes.class.getClassLoader();

        String path = getPath();

        File resourceFromClassLoader = Optional.ofNullable(classLoader1.getResource(path))
                .map(URL::getPath)
                .map(File::new)
                .orElseThrow();

        // Лучше не использовать потому что надо вручную закрывать поток
        // DataInputStream fileInputStream = Optional.ofNullable(classLoader1.getResourceAsStream("config.properties"))
        //         .map(DataInputStream::new)
        //         .orElseThrow();

        List<String> resource = Files.readAllLines(Path.of(resourceFromClassLoader.getPath()));

        // 2. Загружаем файл относительно модуля
        List<String> fileFromModuleRoot = Files.readAllLines(Path.of("build.gradle"));

        // 3. Если файл находится за пределами модуля, то используем полный путь
        List<String> absolutPathFile = Files.readAllLines(Path.of("/home/saeed/code/code-recepies/version"));

        return resourceFromClassLoader.exists()
               && !resource.isEmpty()
               && !fileFromModuleRoot.isEmpty()
               && !absolutPathFile.isEmpty();
    }

    private static String getPath() {
        // String path = String.join(File.separator, "config", "config.properties");
        Path path = Path.of("config", "config.properties");
        return path.toFile().getPath();
    }

    @SneakyThrows
    public static void readFileSpeed() {
        ClassLoader classLoader1 = Thread.currentThread().getContextClassLoader();
        File resourceFromClassLoader = Optional.ofNullable(classLoader1.getResource("large.txt"))
                .map(URL::getPath)
                .map(File::new)
                .orElseThrow();

        // ~ 26 ms
        try (FileInputStream fileInputStream = new FileInputStream(resourceFromClassLoader)) {
            long start = System.currentTimeMillis();
            String s = new String(fileInputStream.readAllBytes());
            System.out.println("FileInputStream Total time: " + (System.currentTimeMillis() - start));
        }

        // ~ 121 ms Или readLine(), но обязательно через StringBuilder
        try (var bufferedReader = new BufferedReader(new FileReader(resourceFromClassLoader))) {
            long start = System.currentTimeMillis();
            String s = bufferedReader.lines().collect(Collectors.joining());
            System.out.println("BufferedReader Total time: " + (System.currentTimeMillis() - start));
        }

        // Считывание по одному байту очень долгое ~ 12.5 сек для файла 1.4 Мб
        // try (FileInputStream fileInputStream = new FileInputStream(resourceFromClassLoader)) {
        //     long start = System.currentTimeMillis();
        //     byte[] bytes = new byte[fileInputStream.available()];
        //     int currentByte;
        //     int count = 0;
        //     while ((currentByte = fileInputStream.read()) != -1) {
        //         bytes[count] = (byte)currentByte;
        //         count++;
        //     }
        //
        //     System.out.println("Total time: " + (System.currentTimeMillis() - start));
        // }

        // 27 ms
        long start1 = System.currentTimeMillis();
        String allBytes = new String(Files.readAllBytes(Path.of(resourceFromClassLoader.getPath())));
        System.out.println("Files.readAllBytes Total time: " + (System.currentTimeMillis() - start1));

        // 127 ms
        long start2 = System.currentTimeMillis();
        List<String> allLines = Files.readAllLines(Path.of(resourceFromClassLoader.getPath()));
        System.out.println("Files.readAllLines Total time: " + (System.currentTimeMillis() - start2));

        // 155 ms
        long start3 = System.currentTimeMillis();
        List<String> lines = Files.lines(Path.of(resourceFromClassLoader.getPath())).toList();
        System.out.println("Files.lines Total time: " + (System.currentTimeMillis() - start3));
    }

    @SneakyThrows
    public static void writeFile(String fileName, byte[] content) {

        Path filePath = Path.of("/home/saeed/code/code-recepies/storage").resolve(fileName);
        Files.deleteIfExists(filePath);

        ClassLoader classLoader1 = Thread.currentThread().getContextClassLoader();

        // Писать в ресурсы не требуется, обычно указывают какую-то папку на сервере в которую
        // уже записываются файлы
        /*File resourceFromClassLoader = Optional.ofNullable(classLoader1.getResource(Path.of("config", "write.txt").toFile().getPath()))
                .map(URL::getPath)
                .map(File::new)
                .orElseThrow();*/



        /*try (var os = new FileOutputStream(resourceFromClassLoader)) {
            long start = System.currentTimeMillis();
            os.write(content);
            os.write(System.lineSeparator().getBytes());
            System.out.println("FileInputStream Total time: " + (System.currentTimeMillis() - start));
        }*/

//        try (var os = new FileOutputStream(filePath.toFile())) {
        try (var os = Files.newOutputStream(filePath)) {
            long start = System.currentTimeMillis();
            os.write(content);
            os.write(System.lineSeparator().getBytes());
            System.out.println("FileInputStream Total time: " + (System.currentTimeMillis() - start));
        }

        // +
        try (var os = Files.newByteChannel(filePath, StandardOpenOption.WRITE)) {
            long start = System.currentTimeMillis();
            os.write(ByteBuffer.wrap(content));
            System.out.println("newByteChannel Total time: " + (System.currentTimeMillis() - start));
        }
        // +
        try(FileChannel channel = FileChannel.open(filePath, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            long start = System.currentTimeMillis();
            channel.write(ByteBuffer.wrap(content));
            System.out.println("FileChannel Total time: " + (System.currentTimeMillis() - start));
        }

        // Safe but slower
        ByteBuffer buf = ByteBuffer.wrap(content);
        try(FileChannel channel = FileChannel.open(filePath, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            long start = System.currentTimeMillis();
            while (buf.hasRemaining()) {
                int bytes = channel.write(buf);
                if (bytes <= 0) {
                    break;
                }
            }
            channel.force(true);
            System.out.println("FileChannel Total time: " + (System.currentTimeMillis() - start));
        }

        // FASTEST!!! BUT DANGEROUS
        try(var channel = AsynchronousFileChannel.open(filePath, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            long start = System.currentTimeMillis();
            Future<Integer> write = channel.write(ByteBuffer.wrap(content), 0);
            System.out.println("AsynchronousFileChannel Total time: " + (System.currentTimeMillis() - start));
        }

        // Медленный
        /*try (var os = new FileWriter(filePath.toFile())) {
            long start = System.currentTimeMillis();
            os.write(new String(content));
            System.out.println("FileWriter Total time: " + (System.currentTimeMillis() - start));
        }*/

        /*try (var os = new BufferedOutputStream(new FileOutputStream(resourceFromClassLoader))) {
            long start = System.currentTimeMillis();
            os.write(content);
            os.write(System.lineSeparator().getBytes());
            System.out.println("FileInputStream Total time: " + (System.currentTimeMillis() - start));
        }*/

        try (var os = new BufferedOutputStream(new FileOutputStream(filePath.toFile()), 8192)) {
            long start = System.currentTimeMillis();
            os.write(content);
            os.write(System.lineSeparator().getBytes());
            System.out.println("BufferedOutputStream Total time: " + (System.currentTimeMillis() - start));
        }

        // Медленный
        /*try (var os = new BufferedWriter(new FileWriter(filePath.toFile()), 8192)) {
            long start = System.currentTimeMillis();
            os.append(new String(content));
            System.out.println("BufferedWriter Total time: " + (System.currentTimeMillis() - start));
        }*/

        /*try (var os = Files.newBufferedWriter(filePath)) {
            long start = System.currentTimeMillis();
            os.append(new String(content));
            System.out.println("BufferedWriter Total time: " + (System.currentTimeMillis() - start));
        }*/

        // EASY
        long start = System.currentTimeMillis();
        Files.write(filePath, content, StandardOpenOption.APPEND);
        System.out.println("Files Total time: " + (System.currentTimeMillis() - start));

//        Files.write(Path.of(resourceFromClassLoader.getPath()), content, StandardOpenOption.APPEND);
    }

    // Переносит данные из одного файла в другой, минуя оперативную память приложения
    @SneakyThrows
    public void transferData(Path from, Path to) {
        try(FileChannel reader = FileChannel.open(from, StandardOpenOption.READ);
            FileChannel writer = FileChannel.open(to, StandardOpenOption.WRITE)) {
            long start = System.currentTimeMillis();
            reader.transferTo(0, reader.size(), writer);
            System.out.println("FileChannel Total time: " + (System.currentTimeMillis() - start));
        }
    }

}
