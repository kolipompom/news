package com.clane.publish.news.model.response;

import com.clane.publish.news.model.entity.Token;
import com.clane.publish.news.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kolawole
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class AuthResponse {

  private String token;
  private String expires;
  private String tokenType;

  public static AuthResponse fromToken(Token token) {
    return AuthResponse.builder()
        .token(token.getToken())
        .expires(TimeUtil.getIsoTime(token.getExpiresAt()))
        .tokenType("bearer")
        .build();
  }

}
