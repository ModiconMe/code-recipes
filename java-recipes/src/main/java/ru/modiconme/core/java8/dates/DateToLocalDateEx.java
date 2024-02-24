package ru.modiconme.core.java8.dates;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class DateToLocalDateEx {

    public static void main(String[] args) {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println(localDate);
        Date date1 = Date
                .from(localDate.atTime(LocalTime.now()).atZone(ZoneId.systemDefault()).toInstant());
        System.out.println(date1);

        java.sql.Date date2 = java.sql.Date.valueOf(localDate);
        System.out.println(date2);

        LocalDate localDate1 = date2.toLocalDate();
        System.out.println(localDate1);
    }
}
