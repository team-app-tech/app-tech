package server.apptech.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    NOT_FOUND_ADVERTISEMENT_ID(HttpStatus.BAD_REQUEST, "요청한 ID에 해당하는 광고가 존재하지 않습니다."),

    VALID_CHECK_FAIL(HttpStatus.BAD_REQUEST, "값에 대한 유효성 검사 실패");

    private final HttpStatus httpStatus;
    private final String message;

}
