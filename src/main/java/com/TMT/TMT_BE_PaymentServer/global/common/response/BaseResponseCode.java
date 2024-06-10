package com.TMT.TMT_BE_PaymentServer.global.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BaseResponseCode {
    // Success
    SUCCESS(HttpStatus.OK, true, 200, "요청 응답 성공"),

    //Test
    TEST_ERROR(HttpStatus.BAD_REQUEST, false, 1234, "Test용 Error Message 입니다"),
    //SignUp
    SIGNUP_FAILED(HttpStatus.BAD_REQUEST, false, 1000, "이미 등록된 닉네임입니다."),
    WRONG_NAME_PHONE_NUMBER(HttpStatus.BAD_REQUEST, false, 1001, "이름, 전화번호가 일치하지 않습니다"),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, false, 1002, "찾을수 없는 회원정보"),
    SIGNIN_FAILED(HttpStatus.BAD_REQUEST, false, 1003, "로그인 실패"),
    WRONG_TOKEN(HttpStatus.UNAUTHORIZED, false, 1004, "잘못된 토큰값"),
    EXIST_PHONENUMBER(HttpStatus.BAD_REQUEST, false, 1005, "이미 등록된 전화번호입니다."),
    WRONG_VARIFYCODE(HttpStatus.BAD_REQUEST, false, 1006, "잘못된 인증번호입니다."),

    //payment
    REST_TAMPLATE_WRONG_BODY(HttpStatus.BAD_REQUEST, false, 2000, "올바르지 않은 RequestBody"),
    PAYMENT_SUCCESS(HttpStatus.OK, true, 2001, "결제 플랫폼"),
    
    //KafkaProducer
    FAIL_SEND_MESSAGE(HttpStatus.INTERNAL_SERVER_ERROR, false, 3000, "카프카 메세지 전송실패"),

    //공통 에러. 9000 ~ 9999
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, 9000, "서버 에러"),
    VALIDATION_FAIL_ERROR(HttpStatus.BAD_REQUEST, false, 9100, "(exception error 메세지에 따름)"),
    PATH_VARIABLE_ERROR(HttpStatus.BAD_REQUEST, false, 9200, "잘못된 Path Variable 입력"),
    REQUEST_PARAM_ERROR(HttpStatus.BAD_REQUEST, false, 9300, "잘못된 Request Parameter 입력"),
    NO_HANDLER_FOUND_ERROR(HttpStatus.BAD_REQUEST, false, 9400, "존재 하지 않는 END-POINT"),
    METHOD_NOT_ALLOW_ERROR(HttpStatus.METHOD_NOT_ALLOWED, false, 9500, "(exception error 메세지에 따름)"),
    TOKEN_IS_EXPIRED_ERROR(HttpStatus.UNAUTHORIZED, false, 9999, "(gateway 에서 error 처리)");

    private final HttpStatus httpStatus;
    private final boolean isSuccess;
    private final int code;
    private final String message;
}