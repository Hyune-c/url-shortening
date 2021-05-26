package com.example.urlshortening.converter;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class Base62Converter implements Converter {

  private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
  private static final Integer CHARS_LENGTH = CHARS.length();

  public String encode(Long param) {
    if (param < 0) {
      throw new IllegalArgumentException();
    }

    String result = "0";

    if (param == 0L) {
      return result;
    }

    result = encodeCore(param);

    return result;
  }

  public Long decode(String param) {
    return decodeCore(param);
  }

  private String encodeCore(Long param) {
    StringBuilder sb = new StringBuilder();

    while (param > 0) {
      sb.append(CHARS.charAt((int) (param % CHARS_LENGTH)));
      param /= CHARS_LENGTH;
    }

    return sb.reverse().toString();
  }

  private long decodeCore(String param) {
    long num = 0;

    for (int i = 0; i < param.length(); i++) {
      num = (num * CHARS_LENGTH) + CHARS.indexOf(param.charAt(i));
    }

    return num;
  }
}

