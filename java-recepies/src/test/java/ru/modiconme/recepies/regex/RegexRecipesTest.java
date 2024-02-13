package ru.modiconme.recepies.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class RegexRecipesTest {

    @Test
    void regex1() {
        assertTrue(RegexRecipes.regex1());
    }

    @Test
    void russianFio() {
        assertTrue(RegexRecipes.russianFio());
    }

    @Test
    void url() {
        assertTrue(RegexRecipes.urlMatching("https://www.google.com"));
        assertTrue(RegexRecipes.urlMatching("https://www.yandex.ru"));
        assertTrue(RegexRecipes.urlMatching("http://www.yandex.ru"));
    }

    @Test
    void phoneMather() {
        assertTrue(RegexRecipes.phoneMatching("+7 (952) 000 9939"));
        assertTrue(RegexRecipes.phoneMatching("8 (952) 000 9939"));
        assertTrue(RegexRecipes.phoneMatching("+7-(952)-000-9939"));
        assertTrue(RegexRecipes.phoneMatching("8-(952)-000-9939"));
        assertTrue(RegexRecipes.phoneMatching("+7-952-000-9939"));
        assertTrue(RegexRecipes.phoneMatching("8-952-000-9939"));
        assertTrue(RegexRecipes.phoneMatching("89520009939"));
        assertTrue(RegexRecipes.phoneMatching("+7 952 000 9939"));
        assertTrue(RegexRecipes.phoneMatching("8 952 000 9939"));
    }

    @Test
    void emailMatches() {
        assertTrue(RegexRecipes.emailMatching("rebeelsc@gmail.com"));
        assertTrue(RegexRecipes.emailMatching("rebeelsc@gmail.org"));
        assertTrue(RegexRecipes.emailMatching("rebeelsc@yandex.ru"));

        assertFalse(RegexRecipes.emailMatching("1rebeelsc@gmail.org"));
        assertFalse(RegexRecipes.emailMatching("rebeelscgmail.org"));
        assertFalse(RegexRecipes.emailMatching("rebeelsc@gmail.edu"));
        assertFalse(RegexRecipes.emailMatching("rebeelsc@gma.il.org"));
        assertFalse(RegexRecipes.emailMatching("rebe.elsc@gmail.org"));
        assertFalse(RegexRecipes.emailMatching(".rebeelsc@gmail.org"));
        assertFalse(RegexRecipes.emailMatching("rebeelscgmail.org"));
    }

    @Test
    void replaceAll1() {
        String regex = "(<p)([^>].)+>";
        String test = "123asdf <p> <p id=\"p1\"> asd";
        String res = test.replaceAll(regex, "<p>");
        assertEquals("123asdf <p> <p> asd" , res);
    }

    @Test
    void replaceAll2() {
        String regex = "(<p)([^>].)+>";
        String test = "123asdf <p> <p id=\"p1\"> asd";
        String res = test.replaceAll(regex, "<p>");
        assertEquals("123asdf <p> <p> asd" , res);
    }

    @Test
    void phoneFind() {
        String phone = "Hi dear friend, my phone is +7 (952) 000 9939 And your phone is 8 952 000 9939";
        String res = RegexRecipes.phoneFind(phone);
        String[] split = res.split("\n");
        assertEquals("+7 (952) 000 9939", split[0]);
        assertEquals("8 952 000 9939", split[1]);
    }

    @Test
    void phoneFindWithGroup() {
        String phone = "Hi dear friend, my phone is +7 (952) 000 9939 And your phone is 8 952 000 9939";
        String res = RegexRecipes.phoneFindWithGroups(phone);
        String[] split = res.split("\n");
        assertEquals("+7", split[0]);
        assertEquals("8", split[1]);
    }

    @Test
    void replacePhoneFind() {
        String phone = "Hi dear friend, my phone is +7 (952) 000 9939 And your phone is 8 952 000 9939 йцу";
        String res = RegexRecipes.replaceFind(phone);
        assertEquals("Hi dear friend, my phone is 000 - 9939 And your phone is 000 - 9939 йцу", res);
    }

    @Test
    void testMatches() {
        String pattern = "abc";

        String s1 = "abc";
        String s2 = "uabc";
        String s3 = "abcu";

        Pattern compile = Pattern.compile(pattern);
        assertTrue(compile.matcher(s1).matches());
        assertFalse(compile.matcher(s2).matches());
        assertFalse(compile.matcher(s3).matches());
    }

    @Test
    void testFind() {
        String pattern = "abc";

        String s1 = "abc";
        String s2 = "uabc";
        String s3 = "abcu";

        Pattern compile = Pattern.compile(pattern);
        assertTrue(compile.matcher(s1).find());
        assertTrue(compile.matcher(s2).find());
        assertTrue(compile.matcher(s3).find());
    }
}