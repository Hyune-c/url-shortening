package com.example.urlshortening.service;

import com.example.urlshortening.entity.UriTokenEntity;
import com.example.urlshortening.repository.UriTokenRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class FindUriTokenService {

  private final UriTokenRepository uriTokenRepository;

  public FindUriTokenService(UriTokenRepository uriTokenRepository) {
    this.uriTokenRepository = uriTokenRepository;
  }

  public List<UriTokenEntity> findAll() {
    return uriTokenRepository.findAll();
  }
}
