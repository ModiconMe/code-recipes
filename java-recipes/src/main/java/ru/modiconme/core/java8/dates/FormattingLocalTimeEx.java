package ru.modiconme.core.java8.dates;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FormattingLocalTimeEx {

    public static void parseTime() {
        String time1 = "13:00";
        System.out.println(LocalTime.parse(time1, DateTimeFormatter.ISO_LOCAL_TIME));

        String time2 = "13*00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH*mm");
        System.out.println(LocalTime.parse(time2, dateTimeFormatter));
    }

    public static void format() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH*mm");
        System.out.println(LocalTime.now().format(dateTimeFormatter));
    }

    public static void main(String[] args) {
        parseTime();
        format();
    }
}
