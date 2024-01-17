package lt.techin.lectureone.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lt.techin.lectureone.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

//400
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

    @ExceptionHandler({HttpMessageConversionException.class, MethodArgumentNotValidException.class})
    protected ResponseEntity<ErrorResponse> handle(Exception exception) {
        return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                        .message(exception.getMessage())
                        .build());
    }

//401
    @ExceptionHandler({UnauthorizedException.class})
    protected ResponseEntity<ErrorResponse> handle(UnauthorizedException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        ErrorResponse.builder()
                                .message(exception.getMessage())
                                .build()
                );
    }

//404
    @ExceptionHandler({AuthorNotFoundException.class})
    protected ResponseEntity<ErrorResponse> handle(AuthorNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.builder()
                                .message(exception.getMessage())
                                .build()
                );
    }

// 5XX
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
}
