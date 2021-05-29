package com.example.urlshortening.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.urlshortening.config.JpaConfiguration;
import com.example.urlshortening.data.TestData;
import com.example.urlshortening.entity.UriTokenEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.dao.DataIntegrityViolationException;

@Slf4j
@DisplayName("[repo] uriTokenEntity")
@DataJpaTest(includeFilters = @ComponentScan.Filter(
    type = FilterType.ASSIGNABLE_TYPE,
    classes = JpaConfiguration.class
))
class UriTokenEntityRepositoryTest {

  @Autowired
  private UriTokenRepository uriTokenRepository;

  public static String[] validUri() {
    return TestData.VALID_URI;
  }

  @DisplayName("[성공] uri 로 생성")
  @MethodSource("validUri")
  @ParameterizedTest
  public void success_create(String uri) {
    // given
    UriTokenEntity crateUriTokenEntity = new UriTokenEntity(uri);

    // when
    uriTokenRepository.save(crateUriTokenEntity);

    // then
    assertThat(crateUriTokenEntity.getUri()).isNotBlank();
    assertThat(crateUriTokenEntity.getCallCount()).isEqualTo(0);

    log.info("### uri {}", crateUriTokenEntity.getUri());
    log.info("### createdAt {}, updatedAt {}", crateUriTokenEntity.getCreatedAt(), crateUriTokenEntity.getUpdatedAt());
  }

  @DisplayName("[성공] uri 로 검색")
  @MethodSource("validUri")
  @ParameterizedTest
  public void success_findByUri(String uri) {
    // given
    uriTokenRepository.save(new UriTokenEntity(uri));

    // when
    UriTokenEntity findUri = uriTokenRepository.findByUri(uri).get();

    // then
    assertThat(findUri.getUri()).isEqualTo(uri);
  }

  public static String[] tooLongUri() {
    return TestData.TOOLONG_URI;
  }

  @DisplayName("[실패] 너무 긴 uri 로 생성")
  @MethodSource("tooLongUri")
  @ParameterizedTest
  public void fail_createTooLong(String tooLongUri) {
    // given
    assertThat(tooLongUri.length()).isGreaterThan(2048);
    UriTokenEntity crateUriTokenEntity = new UriTokenEntity(tooLongUri);

    // when
    assertThrows(DataIntegrityViolationException.class, () -> uriTokenRepository.save(crateUriTokenEntity));

    // then
  }
}
