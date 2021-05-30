package com.example.urlshortening.repository;

import static com.example.urlshortening.common.exception.ErrorCode.ILLEGAL_UPDATE_URI_STATE;
import static com.example.urlshortening.common.exception.ErrorCode.ILLEGAL_URITOKEN_LENGTH;
import static com.example.urlshortening.common.exception.ErrorCode.ILLEGAL_URI_FORMAT;
import static com.example.urlshortening.common.exception.ErrorCode.ILLEGAL_URI_LENGTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.urlshortening.common.exception.custom.BusinessException;
import com.example.urlshortening.config.JpaConfiguration;
import com.example.urlshortening.data.TestData;
import com.example.urlshortening.entity.UriTokenEntity;
import com.example.urlshortening.entity.UriTokenEntity.Property;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

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

  @DisplayName("[실패] 너무 긴 uri 로 생성")
  @Test
  public void fail_create_TooLongUri() {
    // given
    StringBuilder tooLongUri = new StringBuilder(TestData.VALID_URI[0]);
    for (int i = 0; i < Property.URI_MAX_LENGTH + 1; i++) {
      tooLongUri.append("a");
    }

    // when
    BusinessException exception = assertThrows(BusinessException.class, () -> new UriTokenEntity(tooLongUri.toString()));

    // then
    assertThat(exception.getErrorCode()).isEqualTo(ILLEGAL_URI_LENGTH);
  }

  @DisplayName("[실패] 너무 긴 uriToken")
  @MethodSource("validUri")
  @ParameterizedTest
  public void fail_setUriToken_TooLongUriToken(String uri) {
    // given
    UriTokenEntity uriToken = uriTokenRepository.save(new UriTokenEntity(uri));

    StringBuilder tooLongUriToken = new StringBuilder();
    for (int i = 0; i < Property.URITOKEN_MAX_LENGTH + 1; i++) {
      tooLongUriToken.append("a");
    }

    // when
    BusinessException exception = assertThrows(BusinessException.class, () -> uriToken.setUriToken(tooLongUriToken.toString()));

    // then
    assertThat(exception.getErrorCode()).isEqualTo(ILLEGAL_URITOKEN_LENGTH);
  }

  @DisplayName("[실패] uriTokenId 가 존재하지 않는 entity 에 uriToken 등록")
  @Test
  public void fail_setUriToken_notExistsUriTokenId() {
    // given
    UriTokenEntity uriTokenEntity = new UriTokenEntity(TestData.VALID_URI[0]);
    String uriToken = "a";

    // when
    BusinessException exception = assertThrows(BusinessException.class, () -> uriTokenEntity.setUriToken(uriToken));

    // then
    assertThat(exception.getErrorCode()).isEqualTo(ILLEGAL_UPDATE_URI_STATE);
  }

  public static String[] notValidUri() {
    return TestData.NOTVALID_URI;
  }

  @DisplayName("[실패] 잘못된 형식의 uri")
  @MethodSource("notValidUri")
  @ParameterizedTest
  public void fail_create_notValidUri(String notValidUri) {
    // given

    // when
    BusinessException exception = assertThrows(BusinessException.class, () -> new UriTokenEntity(notValidUri));

    // then
    assertThat(exception.getErrorCode()).isEqualTo(ILLEGAL_URI_FORMAT);
  }
}
