package study.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentOverriding {
    private int studentId;

    private String name;

    private String grade;

    /**
     * @param obj 비교 대상
     * @return 필드값 비교를 통해 값이 같은지 여부 리턴
     */
    @Override
    public boolean equals(Object obj) {
        // 두 비교 객체가 같은 객체일 경우 true
        if (this == obj) return true;

        // 객체가 Null이거나 Student의 하위 클래스가 아니라면 false 리턴
        else if (obj == null || !(obj instanceof StudentOverriding)) return false;

        // 다운캐스팅을 통해 obj를 StudentOverriding 인스턴스로 변경 후
        // 비교 주체와 비교 대상의 필드값을 비교하여 모두 같으면 같은 객체로 취급함
        else {
            StudentOverriding studentOverriding = (StudentOverriding) obj;
            boolean result =
                    studentId == studentOverriding.getStudentId() &&
                    name.equals(studentOverriding.getName()) &&
                    grade.equals(studentOverriding.getGrade());

            return result;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(studentId); // StudentId 필드의 해시코드를 반환한다.
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
