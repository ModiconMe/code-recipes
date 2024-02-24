package ru.modiconme.recepies.datetime;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalQueries;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static java.time.format.DateTimeFormatter.*;
import static ru.modiconme.recepies.utils.Utils.split;

/**
 * ZonedDateTime (заменил java.util.Calendar) - самый мощный класс с полной информацией
 * о временном контексте (2007-12-03T10:15:30+01:00 Europe/Paris)
 * <p>
 * Instant (заменил java.util.Date) - представляет точку на временной шкале,
 * не хранит зону (1970-01-01T00:00:00Z)
 * <p>
 * LocalDate (заменил java.sql.Date) - представление только даты без времени и зоны (2018-03-25)
 * LocalTime (заменил java.sql.Time) - представление только времени без даты и зоны (03:56:42.948)
 * LocalDateTime (заменил java.sql.Timestamp) - представление и даты и времени,
 * но без зоны (2018-03-25T03:58:16.570)
 * <p>
 * Если необходимо сохранить зону, то для этого существуют следующие классы (отображают смещение относительно UTC)
 * OffsetTime - представление только времени и зоны (10:15:30+01:00)
 * OffsetDateTime - представление даты, времени и зоны (2018-03-25T04:10:20.369-03:00)
 * <p>
 * Period - описание календарной длительности (периода) в виде кортежа (год, месяц, день)
 * Duration - описание точной длительности в виде целого количества секунд
 * и долей текущей секунды в виде наносекунд
 * <p>
 * DateTimeFormatter - класс определяет настройки форматирования и парсинга
 */
public class DateTimeRecipes {

    public static void main(String[] args) {
        zonedDateTime();

        split();
        localDateTime();

        split();
        periodRecipes();

        split();
        durationRecipes();

        split();
        fromOldApi();

        split();
        dateTimeFormatting();

        Clock clock = Clock.systemDefaultZone();
        clock.millis();

    }

    private static void localDateTime() {
        //        LocalDateTime localNow = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime localNow = LocalDateTime.now(ZoneOffset.UTC);
        System.out.println("LocalDateTime.now(): " + localNow);

        // стирается информация о зоне, но время сдвигается на указанный ZoneOffset
        // считает что localDateTime был в utc и по этому не сдвигает
        Instant localUtcInstant = localNow.toInstant(ZoneOffset.UTC);
        System.out.println("localNow.toInstant(ZoneOffset.UTC): " + localUtcInstant);
        System.out.println("localUtcInstant.toEpochMilli(): " + localUtcInstant.toEpochMilli());

        // считает что localDateTime был в +7 и по этому сдвигает на 7 часов назад
        Instant localOffsetInstant = localNow.toInstant(ZoneOffset.ofHours(7));
        System.out.println("localNow.toInstant(ZoneOffset.ofHours(7)): " + localOffsetInstant);
        System.out.println("localOffsetInstant.toEpochMilli(): " + localOffsetInstant.toEpochMilli());

        /*
         * Modifying localDate
         */
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println("Start date: " + dateTime);
        System.out.println("dateTime.plus(1, ChronoUnit.DAYS): " + dateTime.plus(1, ChronoUnit.DAYS));
        System.out.println("dateTime.plusDays(1): " + dateTime.plusDays(1));
        System.out.println("dateTime.truncatedTo(ChronoUnit.MILLIS): " + dateTime.truncatedTo(ChronoUnit.MILLIS));
        System.out.println(dateTime.withYear(2010));
        System.out.println(dateTime.with(ChronoField.YEAR, 2020));
        System.out.println(dateTime.with(TemporalAdjusters.firstDayOfNextMonth()));

        /*
         * Get values from localDateTime
         */
        System.out.println("dateTime.getSecond()); " + dateTime.getSecond());
        System.out.println("dateTime.getMinute()); " + dateTime.getMinute());
        System.out.println("dateTime.getHour()); " + dateTime.getHour());
        System.out.println("dateTime.getMonth()); " + dateTime.getMonth());
        System.out.println("dateTime.getMonthValue()); " + dateTime.getMonthValue());
        System.out.println("dateTime.getDayOfWeek()); " + dateTime.getDayOfWeek());
        System.out.println("dateTime.get(ChronoField.DAY_OF_MONTH)); " + dateTime.get(ChronoField.DAY_OF_MONTH));

        /*
         * Additional support methods
         */
        System.out.println("Високосный год ? " + dateTime.toLocalDate().isLeapYear());
        System.out.println("Даты равны ? " + dateTime.isEqual(LocalDateTime.now()));
        System.out.println("Дата раньше ? " + dateTime.isBefore(LocalDateTime.now().minusDays(1)));
        System.out.println("Дата позже ? " + dateTime.isAfter(LocalDateTime.now().minusDays(1)));

    }

    private static void zonedDateTime() {
        ZonedDateTime zonedNow = ZonedDateTime.now();
        System.out.println("ZonedDateTime.now(): " + zonedNow);
        System.out.println("zonedNow.getOffset(): " + zonedNow.getOffset());
        System.out.println("zonedNow.getZone(): " + zonedNow.getZone());

        // стирается информация о зоне, сдвигает время в UTC
        Instant zonedInstant = zonedNow.toInstant();
        System.out.println("zonedNow.toInstant(): " + zonedInstant);
        System.out.println("zonedInstant.toEpochMilli(): " + zonedInstant.toEpochMilli());

/*
        System.out.println("ZoneId.getAvailableZoneIds():");
        ZoneId.getAvailableZoneIds()
                .stream()
                .forEach(zi -> System.out.println("- " + zi));
*/
        System.out.println("Count of zoneIds: " + ZoneId.getAvailableZoneIds()
                .stream()
                .count());

        ZoneId zone = ZoneId.of("Europe/Moscow");
        Clock system = Clock.system(ZoneId.of("Asia/Barnaul"));
        System.out.println("Barnaul time: " + ZonedDateTime.now(zone));
        System.out.println("Moscow time: " + ZonedDateTime.now(system));

        // переводит системное время в указанную зону, информация о зоне не сохраняется
        LocalDateTime localDateTimeWithZone = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
        System.out.println("LocalDateTime.now(ZoneId.of(\"Europe/Moscow\")): " + localDateTimeWithZone);

        /*
         * convert from localdatetime, instant to ZonedLocalDate
         */
        // Здесь время не переводиться, потому что LocalDateTime не хранит информацию о зоне
        // просто к переданному времени привязывается указанная зона
        ZonedDateTime ofLocalDateTime = ZonedDateTime.of(localDateTimeWithZone, ZoneId.of("Asia/Barnaul"));
        System.out.println("ZonedDateTime.of(localDateTimeWithZone, ZoneId.of(\"Asia/Barnaul\")): " + ofLocalDateTime);

        ZonedDateTime atZone = localDateTimeWithZone.atZone(ZoneId.of("Asia/Barnaul"));
        System.out.println("localDateTimeWithZone.atZone(ZoneId.of(\"Asia/Barnaul\")): " + atZone);
    }

    private static void periodRecipes() {
        // период между датами
        LocalDate date1 = LocalDate.of(2020, 1, 1);
        LocalDate date2 = LocalDate.of(2020, 12, 31);
        Period until = date1.until(date2);
        System.out.println("date1.until(date2): " + until);
        Period between = Period.between(date1, date2);
        System.out.println("Period.between(date1, date2): " + between);

        // статический период
        Period ofDays = Period.ofDays(10);
        System.out.println("Period.ofDays(10): " + ofDays); // P10D
        Period ofMonths = Period.ofMonths(10);
        System.out.println("Period.ofMonths(10): " + ofMonths); // P10M
        Period ofYears = Period.ofYears(10);
        System.out.println("Period.ofYears(10): " + ofYears); // P10Y
        Period ofWeeks = Period.ofWeeks(10);
        System.out.println("Period.ofWeeks(10): " + ofWeeks); // P70D
        Period of = Period.of(10, 10, 10);
        System.out.println("Period.of(10, 10, 10): " + of); // P10Y10M10D
    }

    private static void durationRecipes() {
        // Duration между датами
        LocalDateTime dateTime1 = LocalDateTime.now();
        LocalDateTime dateTime2 = dateTime1.minusDays(2);
        long until = dateTime1.until(dateTime2, ChronoUnit.DAYS); // количество дней между датами
        System.out.println("date1.until(date2): " + until);
        Duration between = Duration.between(dateTime1, dateTime2);
        System.out.println("Duration.between(date1, date2): " + between);

        // статический Duration
        Duration ofDays = Duration.ofDays(10);
        System.out.println("Duration.ofDays(10): " + ofDays); // PT240H
        Duration ofHours = Duration.ofHours(10);
        System.out.println("Duration.ofHours(10): " + ofHours); // PT10H
        Duration ofMinutes = Duration.ofMinutes(10);
        System.out.println("Duration.ofMinutes(10): " + ofMinutes); // PT10M
        Duration ofSeconds = Duration.ofSeconds(10);
        System.out.println("Duration.ofSeconds(10): " + ofSeconds); // PT10S
        Duration ofSecondsWithNanoAdjustment = Duration.ofSeconds(10, 10);
        System.out.println("Duration.ofSecondsWithNanoAdjustment(10, 10): " + ofSecondsWithNanoAdjustment); // PT10.00000001S
        Duration ofMillis = Duration.ofMillis(10);
        System.out.println("Duration.ofMillis(10): " + ofMillis); // PT0.01S
        Duration ofNanos = Duration.ofNanos(10);
        System.out.println("Duration.ofNanos(10): " + ofNanos); // PT0.00000001S
        Duration of = Duration.of(10, ChronoUnit.DAYS);
        System.out.println("Duration.of(10, ChronoUnit.DAYS): " + of); // PT240H
    }

    private static void fromOldApi() {

        long epochMilli = Instant.now().toEpochMilli();

        Date date = new Date();
        System.out.println("Date: " + date);
        Date dateFromEpoch = new Date(epochMilli);
        System.out.println("dateFromEpoch: " + dateFromEpoch);
        Date dateFromInstant = Date.from(Instant.now());
        System.out.println("dateFromInstant: " + dateFromInstant);
        Calendar calendarFromInstant = Calendar.getInstance();
        System.out.println("calendarFromInstant: " + calendarFromInstant);
        Calendar calendarFromBuilder = new Calendar.Builder()
                .set(Calendar.YEAR, 2023)
                .set(Calendar.MONTH, Calendar.MARCH)
                .set(Calendar.HOUR, 20)
                .setTimeZone(TimeZone.getTimeZone("Europe/Moscow"))
                .build();
        System.out.println("calendarFromBuilder: " + calendarFromBuilder);

        java.sql.Date sqlDate = new java.sql.Date(epochMilli);
        System.out.println("sqlDate: " + sqlDate);
        Time time = new Time(epochMilli);
        System.out.println("time: " + time);
        Timestamp timestamp = new Timestamp(epochMilli);
        System.out.println("timestamp: " + timestamp);

        // Преобразование
        ZonedDateTime zonedFromOldDate = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        ZonedDateTime zonedFromOldCalendar = ZonedDateTime.ofInstant(calendarFromBuilder.toInstant(), calendarFromBuilder.getTimeZone().toZoneId());
        System.out.println("zonedFromOldDate: " + zonedFromOldDate);
        System.out.println("zonedFromOldCalendar: " + zonedFromOldCalendar);

        LocalDateTime localFromOldDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDateTime localFromOldCalendar = LocalDateTime.ofInstant(calendarFromBuilder.toInstant(), calendarFromBuilder.getTimeZone().toZoneId());
        System.out.println("localFromOldDate: " + localFromOldDate);
        System.out.println("localFromOldCalendar: " + localFromOldCalendar);
    }

    public static Date addDate(int dateField, int value) {
        return addDate(new Date(), dateField, value);
    }

    public static Date addDate(Date date, int dateField, int value) {
        var calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(dateField, value);
        return calendar.getTime();
    }

    public static void dateTimeFormatting() {

        String format = LocalDateTime.now().format(ISO_DATE_TIME);
        LocalDateTime parse = LocalDateTime.parse(format, ISO_DATE_TIME);

        /*
         * Здесь представлены самые распространенные форматы дат, для 5 типов дат:
         * 1) localDateTime - dateTime без зоны
         * 2) zonedDateTime1 - dateTime в московской зоне
         * 3) utcZonedDateTime - dateTime в utc зоне
         * 4) offsetDateTime1 - dateTime c +3 сдвигом
         * 5) utcOffsetDateTime - dateTime с utc 0 сдвигом
         *
         * localDateTime.truncatedTo(SECONDS).format(ISO_LOCAL_DATE_TIME) -> 2024-02-17T14:48:29
         *
         * localDateTime.format("yyyy-MM-dd'T'hh:mm:ss[Z]")        -> 2024-02-17T02:54:10
         * localDateTime.format("yyyy-MM-dd'T'hh:mm:ss[X]")        -> 2024-02-17T02:54:10
         * localDateTime.format("yyyy-MM-dd'T'hh:mm:ss[XXX]")      -> 2024-02-17T02:54:10
         * localDateTime.format(ISO_LOCAL_DATE_TIME)               -> 2024-02-17T14:54:10.123456789
         * localDateTime.format(ISO_OFFSET_DATE_TIME)              -> ❌
         * localDateTime.format(ISO_ZONED_DATE_TIME)               -> ❌
         * localDateTime.format(ISO_DATE_TIME)                     -> 2024-02-17T14:54:10.123456789
         * localDateTime.format(RFC_1123_DATE_TIME)                -> ❌
         * localDateTime.format(ISO_INSTANT)                       -> ❌
         * localDateTime.format(ISO_LOCAL_DATE)                    -> 2024-02-17
         * localDateTime.format(ISO_OFFSET_DATE)                   -> ❌
         * localDateTime.format(BASIC_ISO_DATE)                    -> 20240217
         *
         * zonedDateTime1.format("yyyy-MM-dd'T'hh:mm:ss[Z]")       -> 2024-02-17T02:54:10+0300
         * zonedDateTime1.format("yyyy-MM-dd'T'hh:mm:ss[X]")       -> 2024-02-17T02:54:10+03
         * zonedDateTime1.format("yyyy-MM-dd'T'hh:mm:ss[XXX]")     -> 2024-02-17T02:54:10+03:00
         * zonedDateTime1.format(ISO_LOCAL_DATE_TIME)              -> 2024-02-17T14:54:10.123456789
         * zonedDateTime1.format(ISO_OFFSET_DATE_TIME)             -> 2024-02-17T14:54:10.123456789+03:00
         * zonedDateTime1.format(ISO_ZONED_DATE_TIME)              -> 2024-02-17T14:54:10.123456789+03:00[Europe/Moscow]
         * zonedDateTime1.format(ISO_DATE_TIME)                    -> 2024-02-17T14:54:10.123456789+03:00
         * zonedDateTime1.format(RFC_1123_DATE_TIME)               -> Sat, 17 Feb 2024 14:54:10 +0300
         * zonedDateTime1.format(ISO_INSTANT)                      -> 2024-02-17T11:54:10.123456789Z -3 часа
         * zonedDateTime1.format(ISO_LOCAL_DATE)                   -> 2024-02-17
         * zonedDateTime1.format(ISO_OFFSET_DATE)                  -> 2024-02-17+03:00
         * zonedDateTime1.format(BASIC_ISO_DATE)                   -> 20240217+0300
         *
         *
         * utcZonedDateTime.format("yyyy-MM-dd'T'hh:mm:ss[Z]")     -> 2024-02-17T02:54:10+0000
         * utcZonedDateTime.format("yyyy-MM-dd'T'hh:mm:ss[X]")     -> 2024-02-17T02:54:10Z
         * utcZonedDateTime.format("yyyy-MM-dd'T'hh:mm:ss[XXX]")   -> 2024-02-17T02:54:10Z
         * utcZonedDateTime.format(ISO_LOCAL_DATE_TIME)            -> 2024-02-17T14:54:10.123456789
         * utcZonedDateTime.format(ISO_OFFSET_DATE_TIME)           -> 2024-02-17T14:54:10.123456789Z
         * utcZonedDateTime.format(ISO_ZONED_DATE_TIME)            -> 2024-02-17T14:54:10.123456789Z
         * utcZonedDateTime.format(ISO_DATE_TIME)                  -> 2024-02-17T14:54:10.123456789Z
         * utcZonedDateTime.format(RFC_1123_DATE_TIME)             -> Sat, 17 Feb 2024 14:54:10 GMT
         * utcZonedDateTime.format(ISO_INSTANT)                    -> 2024-02-17T14:54:10.123456789Z
         * utcZonedDateTime.format(ISO_LOCAL_DATE)                 -> 2024-02-17
         * utcZonedDateTime.format(ISO_OFFSET_DATE)                -> 2024-02-17Z
         * utcZonedDateTime.format(BASIC_ISO_DATE)                 -> 20240217Z
         *
         *
         * offsetDateTime1.format("yyyy-MM-dd'T'hh:mm:ss[Z]")      -> 2024-02-17T02:54:10+0300
         * offsetDateTime1.format("yyyy-MM-dd'T'hh:mm:ss[X]")      -> 2024-02-17T02:54:10+03
         * offsetDateTime1.format("yyyy-MM-dd'T'hh:mm:ss[XXX]")    -> 2024-02-17T02:54:10+03:00
         * offsetDateTime1.format(ISO_LOCAL_DATE_TIME)             -> 2024-02-17T14:54:10.123456789
         * offsetDateTime1.format(ISO_OFFSET_DATE_TIME)            -> 2024-02-17T14:54:10.123456789+03:00
         * offsetDateTime1.format(ISO_ZONED_DATE_TIME)             -> 2024-02-17T14:54:10.123456789+03:00
         * offsetDateTime1.format(ISO_DATE_TIME)                   -> 2024-02-17T14:54:10.123456789+03:00
         * offsetDateTime1.format(RFC_1123_DATE_TIME)              -> Sat, 17 Feb 2024 14:54:10 +0300
         * offsetDateTime1.format(ISO_INSTANT)                     -> 2024-02-17T11:54:10.123456789Z -3 часа
         * offsetDateTime1.format(ISO_LOCAL_DATE)                  -> 2024-02-17
         * offsetDateTime1.format(ISO_OFFSET_DATE)                 -> 2024-02-17+03:00
         * offsetDateTime1.format(BASIC_ISO_DATE)                  -> 20240217+0300
         *
         * utcOffsetDateTime.format("yyyy-MM-dd'T'hh:mm:ss[Z]")    -> 2024-02-17T02:54:10+0000
         * utcOffsetDateTime.format("yyyy-MM-dd'T'hh:mm:ss[X]")    -> 2024-02-17T02:54:10Z
         * utcOffsetDateTime.format("yyyy-MM-dd'T'hh:mm:ss[XXX]")  -> 2024-02-17T02:54:10Z
         * utcOffsetDateTime.format(ISO_LOCAL_DATE_TIME)           -> 2024-02-17T14:54:10.123456789
         * utcOffsetDateTime.format(ISO_OFFSET_DATE_TIME)          -> 2024-02-17T14:54:10.123456789Z
         * utcOffsetDateTime.format(ISO_ZONED_DATE_TIME)           -> 2024-02-17T14:54:10.123456789Z
         * utcOffsetDateTime.format(ISO_DATE_TIME)                 -> 2024-02-17T14:54:10.123456789Z
         * utcOffsetDateTime.format(RFC_1123_DATE_TIME)            -> Sat, 17 Feb 2024 14:54:10 GMT
         * utcOffsetDateTime.format(ISO_INSTANT)                   -> 2024-02-17T14:54:10.123456789Z
         * utcOffsetDateTime.format(ISO_LOCAL_DATE)                -> 2024-02-17
         * utcOffsetDateTime.format(ISO_OFFSET_DATE)               -> 2024-02-17Z
         * utcOffsetDateTime.format(BASIC_ISO_DATE)                -> 20240217Z
         *
         */

        LocalDateTime localDateTime = LocalDateTime.of(2024, 2, 17, 14, 54, 10, 123456789);
        OffsetDateTime utcOffsetDateTime = OffsetDateTime.of(localDateTime, ZoneOffset.UTC);
        ZonedDateTime utcZonedDateTime = ZonedDateTime.of(localDateTime, ZoneOffset.UTC);
        OffsetDateTime offsetDateTime1 = OffsetDateTime.of(localDateTime, ZoneOffset.ofHours(3));
        ZonedDateTime zonedDateTime1 = ZonedDateTime.of(localDateTime, ZoneId.of("Europe/Moscow"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss[O]");

        String localDateTimeFormat = localDateTime.format(formatter);
        System.out.println("localDateTimeFormat: " + localDateTimeFormat);
        String zonedDateTimeFormat = zonedDateTime1.format(formatter);
        System.out.println("zonedDateTime1: " + zonedDateTimeFormat);
        String utcZonedDateTimeFormat = utcZonedDateTime.format(formatter);
        System.out.println("utcZonedDateTime: " + utcZonedDateTimeFormat);
        String offsetDateTimeFormat = offsetDateTime1.format(formatter);
        System.out.println("offsetDateTime1: " + offsetDateTimeFormat);
        String utcOffsetDateTimeFormat = utcOffsetDateTime.format(formatter);
        System.out.println("utcOffsetDateTime: " + utcOffsetDateTimeFormat);
    }
}
