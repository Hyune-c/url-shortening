package com.example.urlshortening.service;

import static com.example.urlshortening.common.exception.ErrorCode.NOT_FOUND;

import com.example.urlshortening.common.exception.custom.BusinessException;
import com.example.urlshortening.entity.UriTokenEntity;
import com.example.urlshortening.repository.UriTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UriTokenService {

  private final CreateUriTokenService createUriTokenService;
  private final UriTokenRepository uriTokenRepository;

  public String encode(String uri) {
    Long uriTokenId = createUriTokenService.create(uri);
    UriTokenEntity uriTokenEntity = uriTokenRepository.findById(uriTokenId)
        .orElseThrow(() -> new BusinessException(NOT_FOUND));

    return uriTokenEntity.getUriToken();
  }

  public String decode(String uriToken) {
    UriTokenEntity uriTokenEntity = uriTokenRepository.findByUriToken(uriToken)
        .orElseThrow(() -> new BusinessException(NOT_FOUND));
    uriTokenEntity.addCount();

    return uriTokenEntity.getUri();
  }
}
