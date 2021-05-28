package com.example.urlshortening.controller;

import com.example.urlshortening.config.ApplicationProperty;
import com.example.urlshortening.service.UriTokenService;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UriShorteningThymeleafController {

  private final UriTokenService uriTokenService;

  @GetMapping("/encode")
  private ModelAndView index(ModelAndView modelAndView) {
    modelAndView.addObject("message", "url 을 입력하세요");
    modelAndView.setViewName("index");

    return modelAndView;
  }

  @PostMapping("/encode")
  private ModelAndView encode(@RequestParam("uri") String uri, ModelAndView modelAndView) {
    String uriToken = ApplicationProperty.baseUrl + uriTokenService.encode(uri);

    modelAndView.addObject("message", "url 을 입력하세요");
    modelAndView.addObject("value", uri);
    modelAndView.addObject("result", uriToken);
    modelAndView.setViewName("index");

    return modelAndView;
  }

  @GetMapping("/{uriToken}")
  public void redirect(@PathVariable String uriToken, HttpServletResponse response) throws IOException {
    String uri = uriTokenService.decode(uriToken);
    response.sendRedirect(uri);
  }
}
