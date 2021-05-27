package com.example.urlshortening.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.urlshortening.config.JpaConfiguration;
import com.example.urlshortening.entity.UriTokenEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.dao.DataIntegrityViolationException;

@Slf4j
@DataJpaTest(includeFilters = @ComponentScan.Filter(
    type = FilterType.ASSIGNABLE_TYPE,
    classes = JpaConfiguration.class
))
class UriTokenEntityRepositoryTest {

  @Autowired
  private UriTokenRepository uriTokenRepository;

  public static String[] uri() {
    return new String[]{
        "https://github.com/Hyune-c/url-shortening/issues/3",
        "https://www.google.com/search?q=url+%EC%B6%95%EC%95%BD+%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98&newwindow=1&rlz=1C5CHFA_enKR940KR940&biw=2560&bih=1329&sxsrf=ALeKk030HHsjqSzJ2LvBzbpWBqfjfALSQQ%3A1621991969400&ei=IaKtYJT0F6uUr7wP166w-Ac&oq=url&gs_lcp=Cgdnd3Mtd2l6EAMYATIECCMQJzIECCMQJzIECCMQJzIECAAQQzIHCAAQsQMQQzIICAAQsQMQgwEyAggAMgIIADICCAAyCAgAELEDEIMBOgcIIxCwAxAnOgkIABCwAxAHEB46CQgAELADEAUQHjoICAAQsAMQywE6CQgAELADEAgQHjoHCCMQsAIQJzoECAAQDToICAAQCBANEB46CAgAEA0QBRAeOggIABAHEAoQHjoICAAQCBAHEB46BggAEAcQHjoFCAAQywE6CggAEAcQChAeEBM6CggAEAgQBxAeEBM6CAgAEAcQHhATOgQIABATOgUIABCxA1Cj_AdYkKgIYO-5CGgBcAB4AIABeYgBlgeSAQMwLjiYAQCgAQGqAQdnd3Mtd2l6yAEKwAEB&sclient=gws-wiz"};
  }

  @DisplayName("[성공] uri 로 생성")
  @MethodSource("uri")
  @ParameterizedTest
  public void success_create(String uri) {
    // given
    UriTokenEntity crateUriTokenEntity = new UriTokenEntity(uri);

    // when
    uriTokenRepository.save(crateUriTokenEntity);

    // then
    assertThat(crateUriTokenEntity.getUri()).isNotBlank();
    assertThat(crateUriTokenEntity.getCount()).isEqualTo(0);

    log.info("### uri {}", crateUriTokenEntity.getUri());
    log.info("### createdAt {}, updatedAt {}", crateUriTokenEntity.getCreatedAt(), crateUriTokenEntity.getUpdatedAt());
  }

  @DisplayName("[성공] uri 로 검색")
  @MethodSource("uri")
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
  public void fail_createTooLong() {
    // given
    StringBuilder tooLongUri = new StringBuilder("https://github.com/Hyune-c/");
    while (tooLongUri.length() < 2084) {
      tooLongUri.append("a");
    }
    UriTokenEntity crateUriTokenEntity = new UriTokenEntity(tooLongUri.toString());

    // when
    Assertions.assertThrows(DataIntegrityViolationException.class, () -> uriTokenRepository.save(crateUriTokenEntity));

    // then
  }
}
