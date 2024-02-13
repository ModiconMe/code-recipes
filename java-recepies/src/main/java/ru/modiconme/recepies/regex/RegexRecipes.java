package ru.modiconme.recepies.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexRecipes {
    public static final String PHONE_PATTERN = "^(\\+7|8)[ \\-]?\\(?\\d{3}\\)?[ \\-]?\\d{3}[ \\-]?\\d{4}$";

    // здесь не используются ^ и $ чтобы не указывать начало и конец строки
    public static final String PHONE_FIND_PATTERN = "(\\+7|8)[ \\-]?\\(?\\d{3}\\)?[ \\-]?\\d{3}[ \\-]?\\d{4}";

    public static final String PHONE_REPLACEMENT_PATTERN = "(\\+7|8)[ \\-]?\\(?\\d{3}\\)?[ \\-]?(\\d{3})[ \\-]?(\\d{4})";

        /*
         \\d - цифровой символ
         \\D - не цифровой символ
         \\s - Пробел
         \\S - не пробельный символ
         \\w - буквенный, цифровой или подчеркивание
         \\W - любой, кроме буквенного, цифрового или подчеркивания

         ^ - Начало проверяемой строки
         $ - конец проверяемой строки

         ( | | ) - Или 1) ^(Тайланд|тайланд|Таиланд|таиланд)$ ^Agent 00(1|2|3|4|5|6|7)$
         Аналог ИЛИ - 1) ^[Тт]а[ий]ланд$ 2) ^Agent 00[1-7]$ == ^Agent 00[^890]$

         [a-zA-z] - all english
         [0-9] - \\d
         [^890] - исключения (любой кроме 8 9 0)

         . - любой символ

         Классификаторы:
         + - 1 или более
         * - 0 или более
         ? - 0 или 1
         {n} - ровно n раз
         {m,n} - от m до n включительно
         {m,} - не менее m
         {,n} - не более n
         ^\\w{5}\\s?\\d{3}$ - 1) Agent 007 2) Agent007 3) Agent 006

         */

    public static void main(String[] args) {
    }

    public static boolean regex1() {
        String agent = "Agent 007";
        return agent.matches("^Agent\\s\\d\\d\\d$");
    }

    public static boolean russianFio() {
        String agent = "Жуков Алексей Николаевич";
        return agent.matches("([A-ЯЁа-яё]+[\\s\\-]?){2,}");
    }

    public static boolean urlMatching(String url) {
        String pattern = "http(s)?://www\\..+\\.(com|ru)";
        return url.matches(pattern);
    }

    public static boolean phoneMatching(String phone) {
        String regex = PHONE_PATTERN;
        return phone.matches(regex);
    }

    public static boolean emailMatching(String email) {
        String regex = "^[a-zA-Z]\\w+@\\w+(\\.org|\\.com|\\.ru)$";
        return email.matches(regex);
    }


    public static String phoneFind(String phone) {
        String regex = PHONE_FIND_PATTERN;
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(phone);

        String res = "";
        while (matcher.find()) {
            res += matcher.group();
            res += "\n";
        }
        return res;
    }

    public static String phoneFindWithGroups(String phone) {
        String regex = "(?<name1>\\+7|8)[ \\-]?\\(?\\d{3}\\)?[ \\-]?\\d{3}[ \\-]?\\d{4}";
//        String regex = "(?:\\+7|8)[ \\-]?\\(?\\d{3}\\)?[ \\-]?\\d{3}[ \\-]?\\d{4}"; \\+7|8 - группа исключена
//        String regex = "(\\+7|8)[ \\-]?\\(?\\d{3}\\)?[ \\-]?\\d{3}[ \\-]?\\d{4}\\1"; \\1 в конце означает что должна повторится первая группа
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(phone);

        String res = "";
        while (matcher.find()) {
//            res += matcher.group(1); // (\+7|8) - первая группа
            res += matcher.group("name1");
            res += "\n";
        }
        return res;
    }

    public static String replaceFind(String phone) {
        /*
        String regex = PHONE_REPLACEMENT_PATTERN;
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(phone);

        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "$2 - $3"); // $2 ссылка на группу 2, можно заменить на любую строку
        }
        matcher.appendTail(sb); // сохраняем конец строки, если она заканчивается не на паттерн
        return sb.toString();
        */
        return phone.replaceAll(PHONE_REPLACEMENT_PATTERN, "$2 - $3");
    }
}
