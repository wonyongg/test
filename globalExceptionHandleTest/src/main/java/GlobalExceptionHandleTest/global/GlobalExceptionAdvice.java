package GlobalExceptionHandleTest.global;

import GlobalExceptionHandleTest.exception.BusinessLogicException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * @RestControllerAdvice 애너테이션은 @ControllerAdvice의 기능을 포함하고 있으며,
 * @ResponseBody의 기능 역시 포함하고 있기 때문에 JSON 형식의 데이터를 Response Body로 전송하기 위해서
 * ResponseEntity로 데이터를 래핑할 필요가 없다.
 */
@RestControllerAdvice // 컨트롤러에서 리턴하는 값이 응답 값의 body로 세팅되어 클라이언트에게 전달된다.
public class GlobalExceptionAdvice {

    @ExceptionHandler // 컨트롤러에서 발생한 예외를 잡아 메서드로 처리해주는 기능을 가진 애너테이션이다.
    @ResponseStatus(HttpStatus.BAD_REQUEST) // HTTP Status를 대신 표현할 수 있다.
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final ErrorResponse response = ErrorResponse.of(e.getBindingResult());

        return response;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException e) {
        final ErrorResponse response = ErrorResponse.of(e.getConstraintViolations());

        return response;
    }

    /**
     * @ResponseStatus 애너테이션은 고정된 HttpStatus를 지정하기 때문에
     * BusinessLogicException과 같이 다양한 Status를 동적으로 처리하기 위해
     * ResponseEntity를 리턴값으로 사용한다.
     *
     * @RestControllerAdvice에서 @ResponseStatus를 쓸까? ResponseEntity를 쓸까?
     * 한 가지 유형으로 고정된 예외를 처리할 경우에는 @ResponseStatus로 HttpStatus를 지정해서 사용하면 되고,
     * BusinessLogicException처럼 다양한 유형의 Custom Exception을 처리하고자 할 경우에는
     * ResponseEntity를 사용하면 된다.
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    public ErrorResponse handleBusinessLogicException(BusinessLogicException e) {
        final ErrorResponse response = ErrorResponse.of(e.getExceptionCode().getStatus(), e.getMessage());

        return response;
    }
}
