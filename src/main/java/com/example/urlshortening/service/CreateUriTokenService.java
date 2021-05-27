package com.example.urlshortening.service;

import com.example.urlshortening.converter.Base62Converter;
import com.example.urlshortening.converter.Converter;
import com.example.urlshortening.entity.UriTokenEntity;
import com.example.urlshortening.repository.UriTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateUriTokenService {

  private final UriTokenRepository uriTokenRepository;

  private final Converter converter;

  public CreateUriTokenService(UriTokenRepository uriTokenRepository) {
    this.uriTokenRepository = uriTokenRepository;
    this.converter = new Base62Converter();
  }

  public Long create(String uri) {
    UriTokenEntity uriTokenEntity = uriTokenRepository.findByUri(uri)
        .orElseGet(() -> {
          UriTokenEntity newUriEntity = new UriTokenEntity(uri);
          uriTokenRepository.save(newUriEntity);
          newUriEntity.setUriToken(converter.encode(newUriEntity.getUriTokenId()));

          return newUriEntity;
        });

    return uriTokenEntity.getUriTokenId();
  }
}
