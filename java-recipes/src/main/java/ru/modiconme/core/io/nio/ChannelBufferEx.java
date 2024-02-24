package ru.modiconme.core.io.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelBufferEx {
    public static void main(String[] args) {
        try (
                RandomAccessFile raf = new RandomAccessFile("src/main/resources/file.txt", "rw");
                FileChannel fc = raf.getChannel()
        ) {
            ByteBuffer bb = ByteBuffer.allocate(100);
            StringBuilder sb = new StringBuilder();
            int read;
            while ((read = fc.read(bb)) > 0) {
                System.out.println("Read bytes: " + read);
                bb.flip(); // read mode (cursor goes to begin and start read from buffer)
                while (bb.hasRemaining()){
                   sb.append((char) bb.get());
                }
                bb.clear(); // write mode (cursor goes to begin and start write to buffer)
            }

            System.out.println(sb);
            String text = "Sample text";
//            bb.put(text.getBytes());
//            bb.flip();
//            fc.write(bb);
            ByteBuffer bb2 = ByteBuffer.wrap(text.getBytes());
            fc.write(bb2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ChannelBufferEx1 {
    public static void main(String[] args) {
        try (RandomAccessFile raf = new RandomAccessFile("src/main/resources/file.txt", "r");
             FileChannel fc = raf.getChannel()
        ) {
            int capacity = 5;
            ByteBuffer bb = ByteBuffer.allocate(capacity);
            fc.read(bb);
            bb.flip();
            for (int i = 0; i < 3; i++) {
                System.out.println((char) bb.get());
            }
            bb.rewind(); // position to 0, read-mode
            System.out.print((char) bb.get());
            System.out.print((char) bb.get());
            System.out.println();
            System.out.println("----------");

            bb.compact(); // safe wrote bytes, write new on empty
            fc.read(bb);
            bb.flip();
            for (int i = 0; i < capacity; i++) {
                System.out.print((char) bb.get());
            }
            System.out.println();
            System.out.println("----------");

            fc.read(bb);
            bb.flip();
            System.out.print((char) bb.get());
            bb.mark(); // mark position
            System.out.print((char) bb.get());
            System.out.print((char) bb.get());
            bb.reset(); // come back to the marked position
            System.out.print((char) bb.get());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
