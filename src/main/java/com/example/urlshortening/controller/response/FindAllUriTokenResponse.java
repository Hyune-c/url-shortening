package com.example.urlshortening.controller.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindAllUriTokenResponse {

  private List<UriTokenResponse> uriTokens;

  public FindAllUriTokenResponse(List<UriTokenResponse> uriTokenResponses) {
    this.uriTokens = uriTokenResponses;
  }
}
