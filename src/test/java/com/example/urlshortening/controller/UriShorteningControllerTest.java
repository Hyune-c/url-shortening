package com.example.urlshortening.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.urlshortening.controller.request.EncodeUriRequest;
import com.example.urlshortening.controller.response.EncodeUriResponse;
import com.example.urlshortening.data.BulkTestData;
import com.example.urlshortening.data.TestData;
import com.example.urlshortening.util.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Slf4j
@DisplayName("[web] 통합 테스트")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UriShorteningControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private String url;

  @PostConstruct
  public void postConstruct() {
    url = "/api/v1/encode";
  }

  public static String[] validUri() {
    return TestData.VALID_URI;
  }

  @DisplayName("[성공] uri 를 encode - bulk")
  @MethodSource("validUri")
  @ParameterizedTest
  public void success_create(String uri) throws Exception {
    // given
    EncodeUriRequest request = new EncodeUriRequest(uri);
    RequestBuilder requestBuilder = post(url)
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
    RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
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
    requestBuilder = MockMvcRequestBuilders.get("/" + encodeUriResponse.getUriToken());
    mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().is3xxRedirection());

    // then
  }
}
