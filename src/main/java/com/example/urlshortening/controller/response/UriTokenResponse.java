package com.example.urlshortening.controller.response;

import com.example.urlshortening.entity.UriTokenEntity;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UriTokenResponse implements Serializable {

  private Long uriTokenId;
  private String uriToken;
  private String uri;
  private Long count;

  public UriTokenResponse(UriTokenEntity uriToken) {
    this.uriTokenId = uriToken.getUriTokenId();
    this.uriToken = uriToken.getUriToken();
    this.uri = uriToken.getUri();
    this.count = uriToken.getCallCount();
  }
}
