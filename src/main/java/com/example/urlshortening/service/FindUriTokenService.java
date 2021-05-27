package com.example.urlshortening.service;

import com.example.urlshortening.entity.UriTokenEntity;
import com.example.urlshortening.repository.UriTokenRepository;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FindUriTokenService {

  private final UriTokenRepository uriTokenRepository;

  public FindUriTokenService(UriTokenRepository uriTokenRepository) {
    this.uriTokenRepository = uriTokenRepository;
  }

  public UriTokenEntity findByUriToken(String uriToken) {
    return uriTokenRepository.findByUriToken(uriToken)
        .orElseThrow(EntityNotFoundException::new);
  }

  public UriTokenEntity findByUri(String uri) {
    return uriTokenRepository.findByUri(uri)
        .orElseThrow(EntityNotFoundException::new);
  }

  public UriTokenEntity findById(Long uriTokenId) {
    return uriTokenRepository.findById(uriTokenId)
        .orElseThrow(EntityNotFoundException::new);
  }
}
