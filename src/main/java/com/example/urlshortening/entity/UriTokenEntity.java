package com.example.urlshortening.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "uri_token")
@Entity
public class UriTokenEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long uriTokenId;

  private String uriToken;

  @Column(length = 2048)
  private String uri;

  private Long count;

  @CreatedDate
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  public UriTokenEntity(String newUri) {
    uri = newUri;

    count = 0L;
  }

  public void addCount() {
    count++;
  }

  public Long getUriTokenId() {
    return uriTokenId;
  }

  public String getUriToken() {
    return uriToken;
  }

  public String getUri() {
    return uri;
  }

  public Long getCount() {
    return count;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}
