package server.apptech.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InvalidPaymentException extends RuntimeException{
    private final ExceptionCode exceptionCode;
}
