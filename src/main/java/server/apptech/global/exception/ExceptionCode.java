package server.apptech.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    NOT_FOUND_ADVERTISEMENT_ID(HttpStatus.BAD_REQUEST, "요청한 ID에 해당하는 광고가 존재하지 않습니다."),
    NOT_FOUND_USER_ID(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자 ID입니다. "),

    VALID_CHECK_FAIL(HttpStatus.BAD_REQUEST, "값에 대한 유효성 검사 실패"),

    FAIL_TO_VALIDATE_TOKEN(HttpStatus.BAD_REQUEST, "토큰 유효성 검사 실패"),
    INVALID_SOCIAL_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 Social Type" ),
    EXPIRED_PERIOD_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "Refresh Token 유효기간 만료"),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST,  "유효하지 않은 Refresh Token"),
    EXPIRED_PERIOD_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "Access Token 유효기간 만료" ),
    INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 Access Token" ),
    INVALID_AUTHORIZATION_HEADER(HttpStatus.BAD_REQUEST, "사용자 인증정보가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
