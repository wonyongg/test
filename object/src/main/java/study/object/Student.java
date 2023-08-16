package study.object;

import lombok.NoArgsConstructor;

/**
 * Object 클래스를 상속받고 있음을 명시적으로 표현하기 위하여 표기
 */
@NoArgsConstructor
public class Student extends Object{

    private int studentId;

    private String name;

    private String grade;

    public Student(int studentId, String name, String grade) {
        this.studentId = studentId;
        this.name = name;
        this.grade = grade;
    }
}
