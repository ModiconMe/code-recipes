package ru.modiconme.core.java8.dates;

import java.time.Instant;

public class InstantEx {

    public static void main(String[] args) {
        Instant now = Instant.now();
        System.out.println(now);

        long epochSecond = now.getEpochSecond();
        System.out.println(epochSecond); // second from 1 january 1970

        System.out.println(Instant.ofEpochSecond(0));
    }
}
