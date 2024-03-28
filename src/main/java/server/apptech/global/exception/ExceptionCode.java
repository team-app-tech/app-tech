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
    INVALID_AUTHORIZATION_HEADER(HttpStatus.BAD_REQUEST, "사용자 인증정보가 존재하지 않습니다."),
    UNAUTHORIZED_USER_ACCESS(HttpStatus.UNAUTHORIZED, "권한이 없는 사용자의 접근입니다!"),

    NOT_FOUND_COMMENT(HttpStatus.BAD_REQUEST, "요청한 ID에 해당하는 댓글이 존재하지 않습니다."),

    NOT_FOUND_FILE(HttpStatus.BAD_REQUEST, "요청한 ID에 해당하는 파일이 존재하지 않습니다."),

    ALREADY_LIKED_ADVERTISEMENT(HttpStatus.BAD_REQUEST, "이미 좋아요를 누른 광고 입니다"),
    ADVERTISEMENT_NOT_LIKED(HttpStatus.BAD_REQUEST, "좋아요를 누르지 않은 광고 입니다."),

    COMMENT_NOT_LIKED(HttpStatus.BAD_REQUEST, "좋아요를 누르지 않은 댓글입니다" ),
    ALREADY_LIKED_COMMENT(HttpStatus.BAD_REQUEST,"이미 좋아요를 누른 댓글입니다."),

    INVALID_SORT_OPTION(HttpStatus.BAD_REQUEST, "존재하지않는 SortOption 값입니다."),

    INVALID_PAYMENT_REQUEST(HttpStatus.BAD_REQUEST, "유효하지 않은 결제 요청입니다." ),

    NOT_FOUND_COMMENT_REPLY(HttpStatus.BAD_REQUEST, "존재하지 않는 답글입니다."),
    ALREADY_LIKED_COMMENT_REPLY(HttpStatus.BAD_REQUEST, "이미 좋아요를 누른 답글입니다."),
    COMMENT_REPLY_NOT_LIKED(HttpStatus.BAD_REQUEST,"좋아요를 누르지 않은 답글입니다."),
    NOT_FOUND_IMAGE(HttpStatus.BAD_REQUEST, "요청한 ID에 해당하는 파일이 이미지가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
