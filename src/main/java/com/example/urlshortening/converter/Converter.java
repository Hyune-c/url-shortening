package com.example.urlshortening.converter;

public interface Converter {

  String encode(Long param);

  Long decode(String param);
}
