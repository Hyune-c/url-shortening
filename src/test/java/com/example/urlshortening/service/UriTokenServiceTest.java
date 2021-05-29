package com.example.urlshortening.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.urlshortening.data.TestData;
import com.example.urlshortening.entity.UriTokenEntity;
import com.example.urlshortening.repository.UriTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@DisplayName("[service] uriToken 통합 서비스")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UriTokenServiceTest {

  @Autowired
  private UriTokenService uriTokenService;

  @Autowired
  private UriTokenRepository uriTokenRepository;

  public static String[] validUri() {
    return TestData.VALID_URI;
  }

  @DisplayName("[성공] uri 로 encode ")
  @MethodSource("validUri")
  @ParameterizedTest
  public void success_encode(String uri) {
    // given

    // when
    String uriToken = uriTokenService.encode(uri);

    // then
    assertThat(uriToken).isNotBlank();
    log.info("### token: {} ", uriToken);
  }

  @DisplayName("[성공] uriToken 으로 decode")
  @MethodSource("validUri")
  @ParameterizedTest
  public void success_decode(String uri) {
    // given
    String uriToken = uriTokenService.encode(uri);

    // when
    String decodeUri = uriTokenService.decode(uriToken);

    // then
    UriTokenEntity uriTokenEntity = uriTokenRepository.findByUri(decodeUri).get();
    assertThat(uriTokenEntity.getUriToken()).isEqualTo(uriToken);
    assertThat(uriTokenEntity.getUri()).isEqualTo(decodeUri);
    assertThat(uriTokenEntity.getCallCount()).isGreaterThan(0);
  }

  @DisplayName("[성공] uriToken 으로 decode - count 증가")
  @MethodSource("validUri")
  @ParameterizedTest
  public void success_decode_increaseCount(String uri) {
    // given
    String uriToken = uriTokenService.encode(uri);

    for (int i = 1; i < 10; i++) {
      // when
      String decodeUri = uriTokenService.decode(uriToken);

      // then
      UriTokenEntity uriTokenEntity = uriTokenRepository.findByUri(decodeUri).get();
      assertThat(uriTokenEntity.getUriToken()).isEqualTo(uriToken);
      assertThat(uriTokenEntity.getUri()).isEqualTo(decodeUri);
      assertThat(uriTokenEntity.getCreatedAt()).isBefore(uriTokenEntity.getUpdatedAt());
      assertThat(uriTokenEntity.getCallCount()).isEqualTo(i);
    }
  }
}
