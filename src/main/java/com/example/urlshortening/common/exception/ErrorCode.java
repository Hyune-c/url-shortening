package com.example.urlshortening.common.exception;

import static com.example.urlshortening.entity.UriTokenEntity.Property.URITOKEN_MAX_LENGTH;
import static com.example.urlshortening.entity.UriTokenEntity.Property.URI_MAX_LENGTH;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  MUST_GREATER_THAN_0("E1001", HttpStatus.CONFLICT, "입력 값은 0 보다 커야 합니다."),
  ILLEGAL_URITOKEN_LENGTH("E1002", HttpStatus.CONFLICT, "URITOKEN 은 " + URITOKEN_MAX_LENGTH + " 보다 짧아야 합니다."),
  ILLEGAL_URI_LENGTH("E1003", HttpStatus.CONFLICT, "URI 는 " + URI_MAX_LENGTH + " 보다 짧아야 합니다."),
  ILLEGAL_UPDATE_URI_STATE("E1004", HttpStatus.CONFLICT, "URI entity 를 수정할 수 없는 상태입니다."),
  ILLEGAL_URI_FORMAT("E1005", HttpStatus.CONFLICT, "잘못된 형식의 URI"),

  // 기타
  BAD_REQUEST("E0400", HttpStatus.BAD_REQUEST, "잘못된 입력 값"),
  UNAUTHORIZED("E0401", HttpStatus.UNAUTHORIZED, "인증 실패"),
  FORBIDDEN("E0403", HttpStatus.FORBIDDEN, "권한 없음"),
  NOT_FOUND("E0404", HttpStatus.NOT_FOUND, "찾을 수 없음"),
  METHOD_NOT_ALLOWED("E0405", HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메소드"),
  UNKNOWN("E0500", HttpStatus.INTERNAL_SERVER_ERROR, "알수 없는 서버 에러");

  private final String code;
  private final HttpStatus status;
  private final String reason;
}
