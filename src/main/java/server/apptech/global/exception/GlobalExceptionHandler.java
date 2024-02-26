package server.apptech.global.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleCustomException(RestApiException e){
        ExceptionCode errorCode = e.getExceptionCode();
        return handleExceptionInternal(errorCode);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ExceptionCode errorCode = ExceptionCode.VALID_CHECK_FAIL;
        return handleExceptionInternal(ex, errorCode);
    }

    private ResponseEntity<Object> handleExceptionInternal(ExceptionCode exceptionCode){
        return ResponseEntity.status(exceptionCode.getHttpStatus())
                .body(ExceptionResponse.builder()
                        .code(exceptionCode.name())
                        .message(exceptionCode.getMessage())
                        .build());
    }

    private ResponseEntity<Object> handleExceptionInternal(MethodArgumentNotValidException ex, ExceptionCode exceptionCode){
        List<ValidationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map((fieldError) -> ValidationError.of(fieldError))
                .collect(Collectors.toList());

        return ResponseEntity.status(exceptionCode.getHttpStatus())
                .body(ExceptionResponse.builder()
                        .code(exceptionCode.name())
                        .message(exceptionCode.getMessage())
                        .errors(validationErrors)
                        .build());
    }
}
