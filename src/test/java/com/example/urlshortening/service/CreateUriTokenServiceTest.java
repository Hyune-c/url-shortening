package com.example.urlshortening.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class CreateUriTokenServiceTest {

  @Autowired
  private CreateUriTokenService createUriTokenService;

  public static String[] uri() {
    return new String[]{
        "https://github.com/Hyune-c/url-shortening/issues/3",
        "https://www.google.com/search?q=url+%EC%B6%95%EC%95%BD+%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98&newwindow=1&rlz=1C5CHFA_enKR940KR940&biw=2560&bih=1329&sxsrf=ALeKk030HHsjqSzJ2LvBzbpWBqfjfALSQQ%3A1621991969400&ei=IaKtYJT0F6uUr7wP166w-Ac&oq=url&gs_lcp=Cgdnd3Mtd2l6EAMYATIECCMQJzIECCMQJzIECCMQJzIECAAQQzIHCAAQsQMQQzIICAAQsQMQgwEyAggAMgIIADICCAAyCAgAELEDEIMBOgcIIxCwAxAnOgkIABCwAxAHEB46CQgAELADEAUQHjoICAAQsAMQywE6CQgAELADEAgQHjoHCCMQsAIQJzoECAAQDToICAAQCBANEB46CAgAEA0QBRAeOggIABAHEAoQHjoICAAQCBAHEB46BggAEAcQHjoFCAAQywE6CggAEAcQChAeEBM6CggAEAgQBxAeEBM6CAgAEAcQHhATOgQIABATOgUIABCxA1Cj_AdYkKgIYO-5CGgBcAB4AIABeYgBlgeSAQMwLjiYAQCgAQGqAQdnd3Mtd2l6yAEKwAEB&sclient=gws-wiz"};
  }

  @DisplayName("[성공] token 생성")
  @MethodSource("uri")
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
