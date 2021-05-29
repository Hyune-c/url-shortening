package com.example.urlshortening.controller;

import com.example.urlshortening.controller.request.EncodeUriRequest;
import com.example.urlshortening.controller.response.EncodeUriResponse;
import com.example.urlshortening.controller.response.FindAllUriTokenResponse;
import com.example.urlshortening.controller.response.UriTokenResponse;
import com.example.urlshortening.entity.UriTokenEntity;
import com.example.urlshortening.service.FindUriTokenService;
import com.example.urlshortening.service.UriTokenService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UriShorteningController {

  private final UriTokenService uriTokenService;
  private final FindUriTokenService findUriTokenService;

  @PostMapping("/api/v1/encode")
  public EncodeUriResponse encode(@RequestBody EncodeUriRequest request) {
    String uriToken = uriTokenService.encode(request.getUri());
    return new EncodeUriResponse(uriToken);
  }

  @Cacheable(value = "findAllUriTokenCache")
  @GetMapping("/api/v1/uriTokens")
  public FindAllUriTokenResponse findAllUriTokens() {
    List<UriTokenEntity> uriTokens = findUriTokenService.findAll();
    return new FindAllUriTokenResponse(uriTokens.stream()
        .map(UriTokenResponse::new)
        .collect(Collectors.toList()));
  }
}
