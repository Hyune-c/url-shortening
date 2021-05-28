package com.example.urlshortening.service;

import static com.example.urlshortening.common.exception.ErrorCode.NOT_FOUND;

import com.example.urlshortening.common.exception.custom.BusinessException;
import com.example.urlshortening.entity.UriTokenEntity;
import com.example.urlshortening.repository.UriTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class FindUriTokenService {

  private final UriTokenRepository uriTokenRepository;

  public FindUriTokenService(UriTokenRepository uriTokenRepository) {
    this.uriTokenRepository = uriTokenRepository;
  }

  public UriTokenEntity findByUriToken(String uriToken) {
    return uriTokenRepository.findByUriToken(uriToken)
        .orElseThrow(() -> new BusinessException(NOT_FOUND));
  }

  public UriTokenEntity findByUri(String uri) {
    return uriTokenRepository.findByUri(uri)
        .orElseThrow(() -> new BusinessException(NOT_FOUND));
  }

  public UriTokenEntity findById(Long uriTokenId) {
    return uriTokenRepository.findById(uriTokenId)
        .orElseThrow(() -> new BusinessException(NOT_FOUND));
  }
}
