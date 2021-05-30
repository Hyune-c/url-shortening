package com.example.urlshortening.controller.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EncodeUriRequest {

  private String uri;

  public EncodeUriRequest(String newUri) {
    uri = newUri;
  }
}
