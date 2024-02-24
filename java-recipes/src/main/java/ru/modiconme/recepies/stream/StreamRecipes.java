package ru.modiconme.recepies.stream;

import com.google.common.collect.Sets;
import lombok.SneakyThrows;
import ru.modiconme.core.java8.data.Student;
import ru.modiconme.core.java8.data.StudentDataBase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Supplier;
import java.util.random.RandomGenerator;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static ru.modiconme.recepies.utils.Utils.split;

/**
 * Мотивация стримов ? Параллелизм (parallelStream)
 * <p>
 * Большая часть кода укладывается в простой паттерн
 * source -> op -> op -> op -> sink
 * - sources: collections, iterators, channels, ...
 * - operations: filter, map, reduce, ...
 * - sinks: collections, locals, ...
 * <p>
 * Stream это
 * 1) Множество значений
 * 2) Не структура данных (no storage)
 * 3) Выполнение операций отложено до последнего (lazy) - выполняется только в терминальных операторах
 * 4) Может быть бесконечным
 * 5) Не мутирует источник
 * 6) Одноразовый
 * 7) Ordered/Unordered
 * 8) Parallel/Sequential
 * 9) Примитивные специализации - IntStream, LongStream, DoubleStream
 * <p>
 * Parallelism
 * - многие вещи хорошо бьются на части
 * - многие операции хорошо параллелизуются
 * - библиотека делает всю работу за нас
 * - "под капотом" используется ForkJoinPool
 * - но нужно эксплицитно просить библиотеку parallelStream()
 * <p>
 * Выигрыш от параллелилизма получается когда у нас много дорогостоящих операций (>~1000-10000ms/op)
 * Выигрыш получается не всегда потому что параллельное исполнение тоже не бесплатное и тратятся ресурсы
 * на разбиение на потоки
 */
public class StreamRecipes {

    public static void main(String[] args) {
        streamSources();
        split();

        intermediateOperations();
    }

    public static void streamSources() {
        /*
         * Collections
         *
         * Если у стрима уже есть какая-то характеристика, например distinct,
         * то применение этой операции не будет оказывать никакого влияния
         * и она будет пропущена
         */
        List<Integer> listOf = List.of(1);
        Stream<Integer> streamListOf = listOf.stream(); // sized, ordered

        Set<Integer> setOf = Set.of(1);
        Stream<Integer> streamSetOf = setOf.stream(); // sized, distinct

        TreeSet<Integer> treeSetOf = Sets.newTreeSet(setOf);
        Stream<Integer> streamTreeSetOf = treeSetOf.stream(); // sized, distinct, sorted, ordered

        /*
         * Factories, Builders
         */
        Integer[] intArray = new Integer[]{1, 2, 3};
        Stream<Integer> streamFromIntegerArray1 = Arrays.stream(intArray);
        Stream<Integer> streamFromIntegerArray2 = Stream.of(intArray);
        Stream<Integer> streamFromIntegerArray3 = Stream.of(1, 2, 3);

        /* ❌ Получается стрим Object
        Stream<Object> streamFromIntegerArray4 = Stream.builder()
                .add(1).add(2).add(3)
                .build();
        */
        IntStream intStreamFromRange = IntStream.range(0, 100);

        /*
         * Generators
         */
        Random generator = new Random();
        Supplier<Integer> nextInt = generator::nextInt;
        Stream.generate(nextInt).limit(10).forEach(System.out::println);

        Stream.iterate(0, x -> x + 1).limit(100).forEach(System.out::println);

        /*
         * Others
         */
        try (Stream<String> lines = Files.lines(Path.of("path/to/file"))) {
        } catch (IOException e) {
        }

        Pattern.compile(" ")
                .splitAsStream("1 qwe 1")
                .forEach(System.out::println);

        DoubleStream doubles = new SplittableRandom().doubles(10);
        doubles.forEach(System.out::println);
    }

    public static void intermediateOperations() {
        Stream<Student> stream = StudentDataBase.getAllStudents().stream();

        /*
         * Stream<S> s;
         * Stream<S> s = s.filter(Predicate<S>); --> Оставляет только те элементы, которые удовлетворяют предикату
         * Stream<T> s = s.map(Function<S, T>); --> преобразует тип элементов стрима согласно функции
         * Stream<T> s = s.flatMap(Function<S, Stream<T>>); --> преобразует каждый элемент стрима в другой стрим и разворачивает его
         * Stream<S> s = s.peek(Consumer<S>); --> совершает некие действия над элементов стрима (обычно используется для дебага)
         * Stream<S> s = s.sorted(); --> сортирует стрим, если еще не был отсортирован
         * Stream<S> s = s.distinct(); --> делает стрим уникальных элементов, если еще не был таким
         * Stream<S> s = s.limit(long); --> пропускает заданное количество элементов
         * Stream<S> s = s.skip(long); --> ограничивает стрим заданным кол-вом элементов
         *
         * Stream<S> s = s.unordered(); --> помечает стрим беспорядночным (не перемешивает его) - для применения оптимизаций
         * Stream<S> s = s.parallel(); --> делает обработку элементов в стриме параллельно
         * Stream<S> s = s.sequential(); --> делает обработку элементов в стриме последовательно
         */

        List<String> list = stream.filter(student -> student.getBike().isPresent())
                .flatMap(student -> student.getActivities().stream())
                .map(activities -> activities.replaceAll("//d+", "1"))
                .peek(System.out::println) // debug - показывает элементы, которые прошли через стрим
                .sorted()
                .distinct()
                .skip(1)
                .limit(10)
                .toList();
        System.out.println(list);
    }

    public static void terminalOperations() {
        /*
         * Терминальные операции дают результат
         * Параллельно или последовательно
         *
         * Можно выделить
         *  итерация: forEach, iterator
         *  поиск: findFirst, findAny
         *  проверка: allMatch, anyMatch, noneMatch
         *  агрегаторы: reduction, collectors
         */

        IntStream.range(0, 100).forEach(System.out::println);

        Iterator<Integer> iterator = Stream.iterate(0, i -> i + 1)
                .limit(100)
                .iterator();

        IntStream intStream = IntStream.range(0, 100);
        int sum1 = intStream.reduce(0, Integer::sum);
        OptionalInt optSum = intStream.reduce(Integer::sum);
        int sum2 = intStream.sum();
        long sum3 = intStream.summaryStatistics().getSum();
        int max = intStream.summaryStatistics().getMax();
        int min = intStream.summaryStatistics().getMin();
        double avg = intStream.summaryStatistics().getAverage();
        long count = intStream.summaryStatistics().getCount();

        /*
         * toList() --> List
         * toSet() --> Set
         *
         * toCollection(Supplier<Collection<T>>) --> Collection<T>
         *
         * partitioningBy(Predicate<T>) --> Map<Boolean, List<T>> - разбиваем по предикату
         * groupingBy(Function<T, K>) --> Map<K, List<T>> - разбиваем по произвольному правилу
         * toMap(Function<T, K>, Function<T, U>) --> Map<K, U>
         */

        // enhanced String builder
        StringJoiner stringJoiner = new StringJoiner(", ");
        stringJoiner.add("Hello");
        stringJoiner.add("World");

    }
}
