package GlobalExceptionHandleTest.exception;

import lombok.Getter;

public class BusinessLogicException extends RuntimeException {

    @Getter
    private ExceptionCode exceptionCode;

    public BusinessLogicException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage()); // RuntimeException의 생성자(super)로 예외 메시지를 전달해준다.
        this.exceptionCode = exceptionCode;
    }
}
