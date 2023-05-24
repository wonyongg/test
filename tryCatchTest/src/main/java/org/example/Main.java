package org.example;

public class Main {
    public static void main(String[] args) {
        test1();
        test2();
    }

    // Test 1
    private static void test1() {
        try {
            System.out.println("<소문자를 대문자로 바꾸는 프로그램>");
            strToUpperCase(null); // 예외 발생
            strToUpperCase("abc");
        }
        catch (ArithmeticException e) { // 예외적인 산술 조건인 경우 ex) 0으로 나누기 등
            System.out.println("ArithmeticException입니다.");
        }
        catch (NullPointerException e) { // 넘겨주는 매개변수가 null인 경우
            System.out.println("NullPointerException입니다.");
            System.out.println("e.getMessage: " + e.getMessage()); // 예외 정보 출력
            System.out.println("e.toString: " + e.toString()); // 예외 정보 출력
        }
        catch (Exception e) { //
            System.out.println("Exception입니다.");
        }
        finally {
            System.out.println("프로그램 종료");
        }
    }

    // Test 2
    static void strToUpperCase(String str) { // 테스트 메서드
        String upperCaseAlphabet = str.toUpperCase();
        System.out.println(upperCaseAlphabet);
    }



    private static void test2() {
        try {
            // Some code that may throw a RuntimeException
            int result = divide(10, 0);
            System.out.println("Result: " + result);
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("Exception caught: " + e.getMessage());
//            throw new RuntimeException("Division by zero");
        }
    }

    public static int divide(int dividend, int divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return dividend / divisor;
    }
}