package com.example.urlshortening.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.urlshortening.controller.request.EncodeUriRequest;
import com.example.urlshortening.controller.response.EncodeUriResponse;
import com.example.urlshortening.data.BulkTestData;
import com.example.urlshortening.data.TestData;
import com.example.urlshortening.repository.UriTokenRepository;
import com.example.urlshortening.service.CreateUriTokenService;
import com.example.urlshortening.util.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.util.StopWatch;
import org.springframework.util.StopWatch.TaskInfo;

@Slf4j
@DisplayName("[web] 통합 테스트")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UriShorteningControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private CreateUriTokenService createUriTokenService;

  @Autowired
  private UriTokenRepository uriTokenRepository;

  private String testUrl;

  @PostConstruct
  public void postConstruct() {
    testUrl = "/api/v1/encode";
  }

  public static String[] validUri() {
    return TestData.VALID_URI;
  }

  @DisplayName("[성공] uri 를 encode - bulk")
  @MethodSource("validUri")
  @ParameterizedTest
  public void success_encode(String uri) throws Exception {
    // given
    EncodeUriRequest request = new EncodeUriRequest(uri);
    RequestBuilder requestBuilder = post(testUrl)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.valueToTree(request).toString());

    // when
    MvcResult result = mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andReturn();

    // then
    EncodeUriResponse response = TestUtil.mvcResultToObject(result, EncodeUriResponse.class);
    assertThat(response.getRedirectUri()).isNotBlank();
    log.info("### response.getRedirectUri: {} ", response.getRedirectUri());
  }

  public static String[] validBulkUri() {
    return BulkTestData.VALID_URI;
  }

  @DisplayName("[성공] uri 를 encode 후 redirect")
  @MethodSource("validBulkUri")
  @ParameterizedTest
  public void success_redirectAfterEncode(String uri) throws Exception {
    // given
    EncodeUriRequest encodeUriRequest = new EncodeUriRequest(uri);
    RequestBuilder requestBuilder = post(testUrl)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.valueToTree(encodeUriRequest).toString());

    // when
    // encode uri
    MvcResult encodeUriResult = mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andReturn();
    EncodeUriResponse encodeUriResponse = TestUtil.mvcResultToObject(encodeUriResult, EncodeUriResponse.class);

    // redirect by uriToken
    requestBuilder = get("/" + encodeUriResponse.getUriToken());
    mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().is3xxRedirection());

    // then
  }

  @DisplayName("[성공] uriToken 전체 조회 - cache")
  @Test
  public void success_findAllUriToken_cache() throws Exception {
    // given
    Arrays.stream(BulkTestData.VALID_URI)
        .forEach(uri -> createUriTokenService.create(uri));
    testUrl = "/api/v1/uriTokens";

    // when
    // not using cache
    RequestBuilder requestBuilder = get(testUrl);
    StopWatch notUsingCacheStopWatch = new StopWatch();
    notUsingCacheStopWatch.start("not using cache");
    mockMvc.perform(requestBuilder)
        .andExpect(status().is2xxSuccessful());
    notUsingCacheStopWatch.stop();

    Long notUsingCacheTime = notUsingCacheStopWatch.getLastTaskTimeNanos();

    // using cache
    StopWatch usingCacheStopWatch = new StopWatch();
    requestBuilder = get(testUrl);
    for (int i = 0; i < 10; i++) {
      usingCacheStopWatch.start("using cache " + i);
      mockMvc.perform(requestBuilder)
          .andExpect(status().is2xxSuccessful());
      usingCacheStopWatch.stop();
    }

    // then
    Arrays.stream(usingCacheStopWatch.getTaskInfo())
        .map(TaskInfo::getTimeNanos)
        .forEach(usingCacheTime -> assertThat(usingCacheTime).isLessThan(notUsingCacheTime));

    log.info(notUsingCacheStopWatch.prettyPrint());
    log.info(usingCacheStopWatch.prettyPrint());
  }
}
