package com.example.urlshortening.controller.request;

import com.example.urlshortening.config.ApplicationProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EncodeUriResponse {

  private String redirectUri;

  public EncodeUriResponse(String uriToken) {
    redirectUri = ApplicationProperty.baseUrl + uriToken;
  }

  public String getUriToken() {
    return redirectUri.split(ApplicationProperty.baseUrl)[1];
  }
}
