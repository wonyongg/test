package org.example;

public class Main {
    public static void main(String[] args) {
        test1();
//        test2();
    }

    // Test 1
    private static void test1() {
        /**
         * 메서드 내 try 문에서 에러가 발생하고 catch에서 잡아 throw를 하지 않으면 그대로 넘어간다.
         */
        System.out.println("-----------------------------------------");
        System.out.println("<소문자를 대문자로 바꾸는 프로그램 - 첫번째 테스트>");
        strToUpperCase(null); // 예외 발생하지만 메서드 내 catch에서 처리하고 끝.
        System.out.println("-----------------------------------------");


        try {
            System.out.println("<대문자를 소문자로 바꾸는 프로그램 - 두번째 테스트>");
            strToLowerCase(null); // 예외 발생
            System.out.println("예외 넘어가는지 테스트"); // 안 넘어감
        }
        catch (ArithmeticException e) { // 예외적인 산술 조건인 경우 ex) 0으로 나누기 등
            System.out.println("ArithmeticException입니다.");
        }
        catch (NullPointerException e) { // 넘겨주는 매개변수가 null인 경우
            System.out.println("NullPointerException입니다.");
            System.out.println("e.getMessage: " + e.getMessage()); // 예외 정보 출력
            System.out.println("e.toString: " + e.toString()); // 예외 정보 출력
            System.out.println("-----------------------------------------");
        }
        catch (Exception e) { //
            System.out.println("Exception입니다.");
        }
        finally {
            System.out.println("프로그램 종료");
        }
    }

    static void strToUpperCase(String str) { // 테스트 메서드 1
        try {
            String upperCaseAlphabet = str.toUpperCase();
            System.out.println(String.format("%s --> %s", str, upperCaseAlphabet));
        }
        catch (Exception e) {
            System.out.println("첫번째 테스트 에러를 catch가 잡아 처리했습니다.");
        };
    }

    static void strToLowerCase(String str) { // 테스트 메서드 2
        String lowerCaseAlphabet = str.toLowerCase(); // 여기서 NullPointerException이 터지고 끝.
        System.out.println(String.format("%s --> %s", str, lowerCaseAlphabet)); // 출력 안 됨
    }


    // Test 2
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