package ru.modiconme.core.java8.dates;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class ComparingTimesDurationEx {

    public static void main(String[] args) {
        LocalTime localTime1 = LocalTime.of(1, 1);
        LocalTime localTime2 = LocalTime.of(2, 1);

        long until = localTime1.until(localTime2, ChronoUnit.MINUTES);
        System.out.println(until);

        Duration between = Duration.between(localTime1, localTime2);
        System.out.println(between);

        Duration ofHours = Duration.ofHours(3);
        System.out.println(ofHours);
    }
}
