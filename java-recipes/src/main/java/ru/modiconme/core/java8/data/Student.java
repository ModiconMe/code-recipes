package ru.modiconme.core.java8.data;

import lombok.Getter;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Value
public class Student {
    String name;
    int gradeLevel;
    double gpa;
    String gender;
    int noteBooks;
    Optional<Bike> bike;
    List<String> activities;
}
