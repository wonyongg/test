package study.object;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class methodOverridingTest {

    @Test
    public void studentEquals() {

        String a = "intp";
        String b = "intp";
        String c = new String("intp");

        System.out.println(a == b);
        System.out.println(a.equals(b));

        System.out.println(a == c);


        Student student1 = new Student(1, "손흥민", "A+");
        Student student2 = new Student(1, "손흥민", "A+");

        System.out.println(student1 == student2);
        System.out.println(student1.equals(student2));
        Assertions.assertNotEquals(student1, student2);
    }

    @Test
    public void studentOverridingEquals() {
        StudentOverriding studentOverriding1 = new StudentOverriding(2, "해리케인", "B");
        StudentOverriding studentOverriding2 = new StudentOverriding(2, "해리케인", "B");

        System.out.println(studentOverriding1.equals(studentOverriding2));
        Assertions.assertEquals(studentOverriding1, studentOverriding2);
    }

    @Test
    public void studentHashCode() {
        Student student1 = new Student();
        Student student2 = new Student();

        System.out.println(student1.hashCode());
        System.out.println(student2.hashCode());

        // List에 student 1,2 추가
        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);

        // List 요소 개수 출력
        System.out.println(studentList.size());

        // Set에 student 1,2 추가
        Set<Student> studentSet = new HashSet<>();
        studentSet.add(student1);
        studentSet.add(student2);

        // Set 요소 개수 출력
        System.out.println(studentSet.size());
    }

    @Test
    public void onlyEquals() {
        StudentOverriding studentOverriding1 = new StudentOverriding();
        StudentOverriding studentOverriding2 = new StudentOverriding();

        // List에 student 1,2 추가
        List<StudentOverriding> studentList = new ArrayList<>();
        studentList.add(studentOverriding1);
        studentList.add(studentOverriding2);

        // List 요소 개수 출력
        System.out.println(studentList.size());

        // Set에 student 1,2 추가
        Set<StudentOverriding> studentSet = new HashSet<>();
        studentSet.add(studentOverriding1);
        studentSet.add(studentOverriding2);

        // Set 요소 개수 출력
        System.out.println(studentSet.size());
    }

    @Test
    public void studentOverridingHashCode() {
        StudentOverriding studentOverriding1 = new StudentOverriding(2, "해리케인", "B");
        StudentOverriding studentOverriding2 = new StudentOverriding(2, "해리케인", "B");

        // List에 student 1,2 추가
        List<StudentOverriding> studentList = new ArrayList<>();
        studentList.add(studentOverriding1);
        studentList.add(studentOverriding2);

        // List 요소 개수 출력
        System.out.println(studentList.size());

        // Set에 student 1,2 추가
        Set<StudentOverriding> studentSet = new HashSet<>();
        studentSet.add(studentOverriding1);
        studentSet.add(studentOverriding2);

        // Set 요소 개수 출력
        System.out.println(studentSet.size());
    }
}
