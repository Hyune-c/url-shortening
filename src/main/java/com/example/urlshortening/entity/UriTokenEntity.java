package com.example.urlshortening.entity;

import static com.example.urlshortening.common.exception.ErrorCode.ILLEGAL_UPDATE_URI_STATE;
import static com.example.urlshortening.common.exception.ErrorCode.ILLEGAL_URITOKEN_LENGTH;
import static com.example.urlshortening.common.exception.ErrorCode.ILLEGAL_URI_FORMAT;
import static com.example.urlshortening.common.exception.ErrorCode.ILLEGAL_URI_LENGTH;

import com.example.urlshortening.common.exception.custom.BusinessException;
import java.beans.PropertyEditor;
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
import org.springframework.beans.propertyeditors.URLEditor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "uri_token")
@Entity
public class UriTokenEntity {

  public static class Property {

    public static final int URITOKEN_MAX_LENGTH = 8;
    public static final int URI_MAX_LENGTH = 2048;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long uriTokenId;

  @Column(length = Property.URITOKEN_MAX_LENGTH)
  private String uriToken; // encode 된 uriTokenId

  @Column(length = Property.URI_MAX_LENGTH)
  private String uri; // 원본 uri

  private Long callCount; // 호출된 횟수

  @CreatedDate
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  public UriTokenEntity(String uri) {
    validationUri(uri);

    this.uri = uri;

    callCount = 0L;
  }

  private void validationUri(String uri) {
    try {
      PropertyEditor urlEditor = new URLEditor();
      urlEditor.setAsText(uri);
    } catch (IllegalArgumentException ex) {
      throw new BusinessException(ILLEGAL_URI_FORMAT);
    }

    if (uri.length() > Property.URI_MAX_LENGTH) {
      throw new BusinessException(ILLEGAL_URI_LENGTH);
    }
  }

  public void setUriToken(String uriToken) {
    if (getUriTokenId() == null) {
      throw new BusinessException(ILLEGAL_UPDATE_URI_STATE);
    }

    if (uriToken.length() > Property.URITOKEN_MAX_LENGTH) {
      throw new BusinessException(ILLEGAL_URITOKEN_LENGTH);
    }

    this.uriToken = uriToken;
  }

  public void addCount() {
    callCount++;
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

  public Long getCallCount() {
    return callCount;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}
