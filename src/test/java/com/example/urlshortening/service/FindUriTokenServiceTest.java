package com.example.urlshortening.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.urlshortening.data.TestData;
import com.example.urlshortening.entity.UriTokenEntity;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

  @DisplayName("[성공] uriToken 전체 조회")
  @Test
  public void success_findAll_cache() {
    // given
    Arrays.stream(TestData.VALID_URI)
        .forEach(uri -> createUriTokenService.create(uri));

    // when
    List<UriTokenEntity> uriTokens = findUriTokenService.findAll();

    // then
    assertThat(uriTokens.size()).isEqualTo(TestData.VALID_URI.length);
  }
}
