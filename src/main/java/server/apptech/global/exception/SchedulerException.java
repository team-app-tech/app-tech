package server.apptech.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SchedulerException extends RuntimeException{
    private final ExceptionCode exceptionCode;
}
