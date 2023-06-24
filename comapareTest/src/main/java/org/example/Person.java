package org.example;


public class Person implements Comparable<Person> {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(Person otherPerson) {

//        return this.age - otherPerson.age; 오름차순
        return otherPerson.age - this.age; // 내림차순
    }
}
