package ru.modiconme.core.generics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * <?> - any class
 * <? extends X> - X or child of X
 * <? super X> - X or super of X
 * <? super CL1&CL2&I1> - class first -> then interface
 */
public class GenericEx {
    public static void main(String[] args) {
        Schoolar schoolar1 = new Schoolar(13, "Jim");
        Schoolar schoolar2 = new Schoolar(15, "John");
        Student student1 = new Student(19, "James");
        Student student2 = new Student(23, "Johny");
        Employee employee1 = new Employee(48, "Bob");
        Employee employee2 = new Employee(32, "Maria");

        /*
         * no type safe
         */
//        Team schoolarTeam = new Team("schoolarTeam");
//        Team studentTeam = new Team("studentTeam");
//        Team employeeTeam = new Team("employeeTeam");
//        studentTeam.addNewParticipant(student1);
//        studentTeam.addNewParticipant(schoolar1);
//        employeeTeam.addNewParticipant(employee1);
//        employeeTeam.addNewParticipant(employee2);
//        schoolarTeam.addNewParticipant(schoolar2);
//        schoolarTeam.addNewParticipant(student2);
//
//        studentTeam.playWith(employeeTeam);
//        studentTeam.playWith(schoolarTeam);

        /*
         * type safe
         */
        Team<Schoolar> schoolarTeam = new Team<>("schoolarTeam");
        Team<Student> studentTeam = new Team<>("studentTeam");
        Team<Employee> employeeTeam = new Team<>("employeeTeam");
        studentTeam.addNewParticipant(student1);
        studentTeam.addNewParticipant(student2);
        employeeTeam.addNewParticipant(employee1);
        employeeTeam.addNewParticipant(employee2);
        schoolarTeam.addNewParticipant(schoolar1);
        schoolarTeam.addNewParticipant(schoolar2);

        studentTeam.playWith(employeeTeam);
        studentTeam.playWith(schoolarTeam);
    }
}

abstract class Participant {
    private int age;
    private String name;

    public Participant(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Schoolar extends Participant {

    public Schoolar(int age, String name) {
        super(age, name);
    }
}

class Student extends Participant {

    public Student(int age, String name) {
        super(age, name);
    }
}

class Employee extends Participant {

    public Employee(int age, String name) {
        super(age, name);
    }
}

//class Team { // no type safe -> we can add any Participant child
//    private String name;
//    private List<Participant> participants;
//
//    public Team(String name) {
//        this.name = name;
//        this.participants = new ArrayList<>();
//    }
//
//    public void addNewParticipant(Participant participant) {
//        participants.add(participant);
//        System.out.println("Team " + name + " has new participant " + participant.getName());
//    }
//
//    public void playWith(Team team) {
//        String winnerName;
//        Random random = new Random();
//        int i = random.nextInt(2);
//        if (i == 0) {
//            winnerName = this.name;
//        } else {
//            winnerName = team.name;
//        }
//        System.out.println("Team " + winnerName + " is win");
//    }
//}

class Team<M extends Participant> {
    private String name;
    private List<M> participants;

    public Team(String name) {
        this.name = name;
        this.participants = new ArrayList<>();
    }

    public void addNewParticipant(M participant) {
        participants.add(participant);
        System.out.println("Team " + name + " has new participant " + participant.getName());
    }

    public void playWith(Team<?> team) {
        String winnerName;
        Random random = new Random();
        int i = random.nextInt(2);
        if (i == 0) {
            winnerName = this.name;
        } else {
            winnerName = team.name;
        }
        System.out.println("Team " + winnerName + " is win");
    }
}
