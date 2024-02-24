package ru.modiconme.core.collections;

import java.util.*;
import java.util.stream.Stream;

public class IterableDemo {

    private static List<String> list1 = new ArrayList<>();
    private static List<String> list2 = new ArrayList<>();

    static {
        list1.add("First");
        list1.add("Second");
        list1.add("Third");
        list1.add("Fourth");
        list1.add("Fifth");
        list2.addAll(list1);
        System.out.println("Start list: " + list1);
    }

    public static void main(String[] args) {
        System.out.println("------------------------------------------------");
        iterator();
        System.out.println("------------------------------------------------");
        listIterator();

        System.out.println("------------------------------------------------");
        String word = "madam";
        System.out.printf("Is word %s palindrome ? %b", word, isPalindrome(word));

        System.out.println();
        System.out.println("------------------------------------------------");
        spliterator();
    }

    private static void iterator() {
        Iterator<String> iterator = list1.iterator();

        while (iterator.hasNext()) {
            System.out.println("Remove value - " + iterator.next());
            iterator.remove();
        }
        System.out.println("Empty list " + list1);
    }

    // can add el
    private static void listIterator() {
        ListIterator<String> listIterator = list2.listIterator();

        while (listIterator.hasNext()) {
            System.out.println("Remove value - " + listIterator.next());
            listIterator.remove();
            listIterator.add("new Value");
        }

        while (listIterator.hasPrevious()) {
            System.out.println("Remove value - " + listIterator.previous());
        }

        System.out.println("Empty list " + list2);
    }

//    private static boolean isPalindrome(String word) {
//        List<Character> characters = new ArrayList<>();
//        for (char ch : word.toCharArray()) {
//            characters.add(ch);
//        }
//
//        System.out.println(characters);
//        ListIterator<Character> listIterator = characters.listIterator();
//        String forw = "";
//        String back = "";
//        while (listIterator.hasNext()) {
//            forw += listIterator.next();
//        }
//        while (listIterator.hasPrevious()) {
//            back += listIterator.previous();
//        }
//        System.out.println(forw);
//        System.out.println(back);
//        if (forw.equals(back))
//            return true;
//        return false;
//    }

    private static boolean isPalindrome(String word) {
        List<Character> characters = new ArrayList<>();
        for (char ch : word.toCharArray()) {
            characters.add(ch);
        }

        System.out.println(characters);
        ListIterator<Character> listIterator1 = characters.listIterator();
        ListIterator<Character> listIterator2 = characters.listIterator(characters.size());
        while (listIterator1.hasNext() && listIterator2.hasPrevious()) {
            if (listIterator1.next() != listIterator2.previous())
                return false;

            if (listIterator1.nextIndex() > word.length() / 2)
                return true;
        }
        return true;
    }

    // can split to two iterators
    private static void spliterator() {
        List<String> stringList = Stream.generate(() -> "Java")
                .limit(10_000_000)
                .toList();

        Spliterator<String> spliterator = stringList.spliterator();

        long beforeI = System.currentTimeMillis();

        spliterator.forEachRemaining((el) -> el += "new");

        long afterI = System.currentTimeMillis();
        System.out.println("1 iterator: " + (afterI - beforeI) + " ms");

        Spliterator<String> spliterator1 = stringList.spliterator();
        Spliterator<String> spliterator2 = spliterator1.trySplit();

        long beforeSI = System.currentTimeMillis();

        if(spliterator2 != null) {
            spliterator2.forEachRemaining((el) -> el += "new");
        }

        spliterator1.forEachRemaining((el) -> el += "new");

        long afterSI = System.currentTimeMillis();
        System.out.println("2 iterators: " + (afterSI - beforeSI) + " ms");

    }
}
