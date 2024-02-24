package ru.modiconme.core.java8.dates;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormattingLocalDateTimeEx {

    public static void parseLocalDateTime() {
        String dateTime = "2018-04-18T14:33:33";
        System.out.println(LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        String customDateTime = "2018/04/18T14:33:33";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH:mm:ss");
        System.out.println(LocalDateTime.parse(customDateTime, dateTimeFormatter));
    }

    public static void formatLocalDateTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH:mm:ss");
        System.out.println(LocalDateTime.now().format(dateTimeFormatter));
    }

    public static void main(String[] args) {
        parseLocalDateTime();
        formatLocalDateTime();
    }
}
