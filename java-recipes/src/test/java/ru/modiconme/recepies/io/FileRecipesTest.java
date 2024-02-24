package ru.modiconme.recepies.io;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class FileRecipesTest {

    @Test
    void getFiles() {
        assertTrue(FileRecipes.readFile());
    }

    @Test
    void readFilesSpeedTest() {
        FileRecipes.readFileSpeed();
    }

    @Test
    void writeFile() {
        Random random = new Random();
        int i = 1024 * 1024 * 200;
        byte[] bytes = new byte[i];
        random.nextBytes(bytes);
        String fileName = "write.txt";
        FileRecipes.writeFile(fileName, bytes);
    }
}