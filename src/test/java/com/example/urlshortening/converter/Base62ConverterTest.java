package com.example.urlshortening.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.urlshortening.data.TestData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@DisplayName("[compnent] base62Converter")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Base62ConverterTest {

  private Converter converter;

  public static String[] validUriTokenId() {
    return TestData.VALID_URITOKENID;
  }

  @DisplayName("[성공] uriTokenId 을 base62 로 encode")
  @ParameterizedTest
  @MethodSource("validUriTokenId")
  public void success_encode(Long uriTokenId) {
    // given
    converter = new Base62Converter();

    // when
    String encoded = converter.encode(uriTokenId);

    // then
    log.info("### param {}, encoded {}, ", uriTokenId, encoded);
    assertThat(converter.decode(encoded)).isEqualTo(uriTokenId);
  }

  public static String[] notValidUriTokenId() {
    return TestData.NOTVALID_URITOKENID;
  }

  @DisplayName("[실패] uriTokenId 을 base62 로 encode - 맞지 않는 uriTokenId")
  @ParameterizedTest
  @MethodSource("notValidUriTokenId")
  public void fail_encode(Long uriTokenId) {
    // given
    converter = new Base62Converter();

    // when
    assertThrows(IllegalArgumentException.class, () -> converter.encode(uriTokenId));

    // then
  }
}
