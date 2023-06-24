package org.example;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        // Comparable Test
        Person moura = new Person("모우라", 27);
        Person kane = new Person("해리케인", 28);
        Person son = new Person("손흥민", 29);

        Person[] people = {moura, kane, son};

        Arrays.sort(people);

        for (Person person : people) {
            System.out.println(person.getName());
        }
    }
}