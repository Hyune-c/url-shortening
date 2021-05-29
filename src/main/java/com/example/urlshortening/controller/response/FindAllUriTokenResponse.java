package com.example.urlshortening.controller.response;

import java.io.Serializable;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindAllUriTokenResponse implements Serializable {

  private List<UriTokenResponse> uriTokens;

  public FindAllUriTokenResponse(List<UriTokenResponse> uriTokenResponses) {
    this.uriTokens = uriTokenResponses;
  }
}
