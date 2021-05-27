package com.example.urlshortening.service;

import com.example.urlshortening.data.TestData;
import com.example.urlshortening.entity.UriTokenEntity;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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

  @DisplayName("[성공] uri 로 uriToken 조회")
  @MethodSource("validUri")
  @ParameterizedTest
  public void success_find(String uri) {
    // given
    Long uriTokenId = createUriTokenService.create(uri);

    // when
    UriTokenEntity uriToken = findUriTokenService.findById(uriTokenId);

    // then
    Assertions.assertThat(uriToken).isNotNull();
  }
}
