package GlobalExceptionHandleTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OuterInnerClassTest {
    int num = 10;
    class InnerA {
        int innerNum = OuterInnerClassTest.this.num;
    }

    static class InnerB {
//        int staticInnerNum = OuterInnerClassTest.this.num; 에러 남
    }

    @Test
    void ref() {
        OuterInnerClassTest a = new OuterInnerClassTest(); // 외부 클래스의 인스턴스 : a
        OuterInnerClassTest.InnerA a1 = a.new InnerA(); // 외부 클래스의 인스턴스에 대한 참조가 필요하다.
        OuterInnerClassTest.InnerA a2 = a.new InnerA();

        Assertions.assertNotEquals(a1, a2);


        OuterInnerClassTest.InnerB b1 = new OuterInnerClassTest.InnerB(); // 외부 클래스의 인스턴스에 대한 참조가 필요없다.
        OuterInnerClassTest.InnerB b2 = new OuterInnerClassTest.InnerB();

        Assertions.assertNotEquals(b1, b2);
    }
}

