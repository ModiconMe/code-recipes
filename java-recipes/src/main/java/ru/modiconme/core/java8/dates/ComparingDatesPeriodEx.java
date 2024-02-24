package ru.modiconme.core.java8.dates;

import java.time.LocalDate;
import java.time.Period;

public class ComparingDatesPeriodEx {

    public static void main(String[] args) {
        LocalDate of1 = LocalDate.of(2020, 1, 1);
        LocalDate of2 = LocalDate.of(2020, 12, 31);

        Period until = of1.until(of2);
        System.out.println(until.getMonths());
        System.out.println(until.getDays());

        Period period = Period.ofDays(10);
        System.out.println(period.getDays());

        Period between = Period.between(of1, of2);
        System.out.println(between);
    }
}
