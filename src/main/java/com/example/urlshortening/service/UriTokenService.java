package com.example.urlshortening.service;

import com.example.urlshortening.entity.UriTokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UriTokenService {

  private final CreateUriTokenService createUriTokenService;
  private final FindUriTokenService findUriTokenService;

  public String encode(String uri) {
    Long uriTokenId = createUriTokenService.create(uri);
    UriTokenEntity uriTokenEntity = findUriTokenService.findById(uriTokenId);

    return uriTokenEntity.getUriToken();
  }

  public String decode(String token) {
    UriTokenEntity uriTokenEntity = findUriTokenService.findByUriToken(token);
    uriTokenEntity.addCount();

    return uriTokenEntity.getUri();
  }
}
