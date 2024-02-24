package ru.modiconme.core.java8.dates;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class LocalDateEx {

    public static void main(String[] args) {
        // LocalDate
        LocalDate now = LocalDate.now();
        LocalDate of = LocalDate.of(2022, 1, 9);
        LocalDate ofYearDay = LocalDate.ofYearDay(2022, 155);
        System.out.println("LocalDate now: " + now);
        System.out.println("LocalDate of: " + of);
        System.out.println("LocalDate ofYearDay: " + ofYearDay);

        /*
         * Get values from localDate
         */
        System.out.println(now.getMonth());
        System.out.println(now.getMonthValue());
        System.out.println(now.getDayOfWeek());
        System.out.println(now.get(ChronoField.DAY_OF_MONTH));

        /*
         * Modifying localDate
         */
        System.out.println(now.plusDays(10));
        System.out.println(now.minusDays(10));
        System.out.println(now.plusYears(10));
        System.out.println(now.withYear(2010));
        System.out.println(now.with(ChronoField.YEAR, 2020));
        System.out.println(now.with(TemporalAdjusters.firstDayOfNextMonth()));
        System.out.println(now.minus(1, ChronoUnit.YEARS));

        /*
         * Additional support methods
         */
        System.out.println(now.isLeapYear());
        System.out.println(now.isEqual(of));
        System.out.println(now.isBefore(of));
        System.out.println(now.isAfter(of));
    }
}
