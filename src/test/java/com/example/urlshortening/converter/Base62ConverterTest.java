package com.example.urlshortening.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class Base62ConverterTest {

  private static final Long BASE62_MAX_VALUE = 218340105584895L;

  private Converter converter;

  public static String[] validEncodeParams() {
    return new String[]{
        String.valueOf(0),
        String.valueOf(1),
        String.valueOf(100),
        String.valueOf(Integer.MAX_VALUE),
        String.valueOf(BASE62_MAX_VALUE)};
  }

  @DisplayName("[성공] long to base64")
  @ParameterizedTest
  @MethodSource("validEncodeParams")
  public void success_encode(Long param) {
    // given
    converter = new Base62Converter();

    // when
    String encoded = converter.encode(param);

    // then
    log.info("### param {}, encoded {}, ", param, encoded);
    assertThat(converter.decode(encoded)).isEqualTo(param);
  }

  public static String[] notValidEncodeParams() {
    return new String[]{String.valueOf(-1)};
  }

  @DisplayName("[실패] long to base64")
  @ParameterizedTest
  @MethodSource("notValidEncodeParams")
  public void fail_encode(Long param) {
    // given
    converter = new Base62Converter();

    // when
    assertThrows(IllegalArgumentException.class, () -> converter.encode(param));

    // then
  }
}
