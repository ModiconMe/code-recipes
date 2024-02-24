package ru.modiconme.core.java8.dates;

import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class LocalTimeEx {

    public static void main(String[] args) {
        LocalTime now = LocalTime.now();
        System.out.println(now);

        LocalTime of1 = LocalTime.of(12, 54);
        LocalTime of2 = LocalTime.of(12, 54, 22);
        LocalTime of3 = LocalTime.of(12, 54, 22, 200278978);

        System.out.println(of1);
        System.out.println(of2);
        System.out.println(of3);

        /*
         * getting values from localtime
         */
        System.out.println(now.getHour());
        System.out.println(now.getMinute());
        System.out.println(now.get(ChronoField.HOUR_OF_DAY));
        System.out.println(now.get(ChronoField.CLOCK_HOUR_OF_DAY));
        System.out.println(now.toSecondOfDay());

        /*
         * modify value of localtime
         */
        System.out.println(now.minus(1, ChronoUnit.HOURS));
        System.out.println(now.with(LocalTime.MIDNIGHT));


    }
}
