package com.example.urlshortening.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperty {

  public static String baseUrl;

  @Value("${application.baseUrl}")
  public void setUrl(String baseUrl) {
    ApplicationProperty.baseUrl = baseUrl;
  }
}
