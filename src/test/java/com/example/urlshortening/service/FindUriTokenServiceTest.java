package com.example.urlshortening.service;

import static com.example.urlshortening.common.exception.ErrorCode.NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.urlshortening.common.exception.custom.BusinessException;
import com.example.urlshortening.data.TestData;
import com.example.urlshortening.entity.UriTokenEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@DisplayName("[service] uriToken 조회")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FindUriTokenServiceTest {

  @Autowired
  private FindUriTokenService findUriTokenService;

  @Autowired
  private CreateUriTokenService createUriTokenService;

  public static String[] validUri() {
    return TestData.VALID_URI;
  }

  @DisplayName("[성공] uriTokenId 로 uriToken 조회")
  @MethodSource("validUri")
  @ParameterizedTest
  public void success_find(String uri) {
    // given
    Long uriTokenId = createUriTokenService.create(uri);

    // when
    UriTokenEntity uriToken = findUriTokenService.findById(uriTokenId);

    // then
    assertThat(uriToken).isNotNull();
  }

  @DisplayName("[실패] uri 로 uriToken 조회")
  @Test
  public void fail_find() {
    // given
    String uri = "a";

    // when
    BusinessException exception = assertThrows(BusinessException.class, () -> findUriTokenService.findByUri(uri));

    // then
    assertThat(exception.getErrorCode()).isEqualTo(NOT_FOUND);
  }
}
