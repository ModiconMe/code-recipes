package ru.modiconme.core.java8.dates;

import java.time.*;

public class ZonedDateTimeEx {

    public static void main(String[] args) {
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);
        System.out.println(now.getOffset());
        System.out.println(now.getZone());

        ZoneId.getAvailableZoneIds()
                .stream()
                .forEach(System.out::println);

        System.out.println(ZoneId.getAvailableZoneIds()
                .stream()
                .count()
        );

        System.out.println("Barnaul CST: " + ZonedDateTime.now(ZoneId.of("Asia/Barnaul")));

        System.out.println(ZonedDateTime.now(Clock.system(ZoneId.of("Asia/Barnaul"))));

        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Europe/Moscow"));
        System.out.println(localDateTime);

        /*
         * convert from localdatetime, instant to ZonedLocalDate
         */
        ZonedDateTime of = ZonedDateTime.of(localDateTime, ZoneId.of("Asia/Barnaul"));
        System.out.println("ZonedDateTime: " + of);

        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Barnaul"));

        ZonedDateTime zonedDateTime1 = Instant.now().atZone(ZoneId.of("Asia/Barnaul"));

    }
}
