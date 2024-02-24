package ru.modiconme.core.java8.dates;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormattingLocalDateEx {

    public static void parseLocalDate() {
        /*
         * String to localDate
         */
        String date = "2020-04-20";
        LocalDate parse = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE); // default
        System.out.println(parse);

        String date1 = "20200420";
        LocalDate parse1 = LocalDate.parse(date1, DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println(parse1);

        /*
         * Custom defined format
         */
        String date2 = "2018|02|20";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy|MM|dd");
        LocalDate parse2 = LocalDate.parse(date2, dateTimeFormatter);
        System.out.println(parse2);

        String date3 = "2018*02*20";
        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy*MM*dd");
        LocalDate parse3 = LocalDate.parse(date3, dateTimeFormatter1);
        System.out.println(parse3);
    }

    public static void formatLocalDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy|MM|dd");
        String format = LocalDate.now().format(dateTimeFormatter);
        System.out.println("Format date: " + format);
    }

    public static void main(String[] args) {
        parseLocalDate();
        formatLocalDate();
    }
}
