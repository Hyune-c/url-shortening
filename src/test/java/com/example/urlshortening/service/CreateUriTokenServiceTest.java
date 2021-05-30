package com.example.urlshortening.service;

import com.example.urlshortening.data.TestData;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@DisplayName("[service] uriToken 생성")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreateUriTokenServiceTest {

  @Autowired
  private CreateUriTokenService createUriTokenService;

  public static String[] validUri() {
    return TestData.VALID_URI;
  }

  @DisplayName("[성공] uriToken 생성")
  @MethodSource("validUri")
  @ParameterizedTest
  public void success_create(String uri) {
    // given

    // when
    Long uriTokenId = createUriTokenService.create(uri);

    // then
    Assertions.assertThat(uriTokenId).isNotNull();
    log.info("### token: {} ", uriTokenId);
  }
}
