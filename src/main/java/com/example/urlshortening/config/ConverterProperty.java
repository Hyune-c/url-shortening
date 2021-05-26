package com.example.urlshortening.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConverterProperty {

  public static Integer maxLength;

  @Value("${application.converter.maxLength}")
  public void setUrl(Integer maxLength) {
    ConverterProperty.maxLength = maxLength;
  }
}
