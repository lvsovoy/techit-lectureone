package lt.techin.lectureone.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lt.techin.lectureone.model.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    protected ResponseEntity<ErrorResponse> handle(ConstraintViolationException exception) {
        return ResponseEntity.badRequest()
                .body(
                        ErrorResponse.builder()
                                .message(exception.getMessage())
                                .cause(exception.getConstraintViolations().toString())
                                .build()
                );
    }

    @ExceptionHandler({ServletException.class})
    protected ResponseEntity<ErrorResponse> handle(ServletException exception, HttpServletRequest httpServletRequest) {
        return ResponseEntity.internalServerError()
                .body(
                        ErrorResponse.builder()
                                .message(String.format("Request %s %s failed", httpServletRequest.getMethod(), httpServletRequest.getRequestURI()))
                                .cause(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler({RuntimeException.class})
    protected ResponseEntity<ErrorResponse> handle(Exception exception) {
        return ResponseEntity.internalServerError()
                .body(ErrorResponse.builder()
                        .message(exception.getMessage())
                        .build());
    }
}
