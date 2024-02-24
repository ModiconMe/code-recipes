package ru.modiconme.core.java8.dates;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeEx {

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        LocalDateTime of1 = LocalDateTime.of(2020, 3, 5, 6, 6, 7, 7);
        System.out.println(of1);

        LocalDateTime of2 = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        System.out.println(of2);

        /*
         * get values and modify is equals with localdate and localtime
         */

        LocalDate localDate = now.toLocalDate();
        LocalTime localTime = now.toLocalTime();
    }
}
