package com.example.urlshortening.repository;

import com.example.urlshortening.entity.UriTokenEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UriTokenRepository extends JpaRepository<UriTokenEntity, Long> {

  Optional<UriTokenEntity> findByUri(String uri);

  Optional<UriTokenEntity> findByUriToken(String uriToken);
}
