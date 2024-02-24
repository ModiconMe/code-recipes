package ru.modiconme.core.java8.dates;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class NewDateTimeEx {

    public static void main(String[] args) {
        // LocalDate
        System.out.println("LocalDate: " + LocalDate.now());
        // LocalTime
        System.out.println("LocalTime: " + LocalTime.now());
        // LocalDateTime
        System.out.println("LocalDateTime: " + LocalDateTime.now());
    }
}
