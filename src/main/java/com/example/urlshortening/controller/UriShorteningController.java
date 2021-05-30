package com.example.urlshortening.controller;

import com.example.urlshortening.controller.request.EncodeUriRequest;
import com.example.urlshortening.controller.request.EncodeUriResponse;
import com.example.urlshortening.service.UriTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UriShorteningController {

  private final UriTokenService uriTokenService;

  @PostMapping("/api/v1/encode")
  public EncodeUriResponse encode(@RequestBody EncodeUriRequest request) {
    String uriToken = uriTokenService.encode(request.getUri());
    return new EncodeUriResponse(uriToken);
  }
}
