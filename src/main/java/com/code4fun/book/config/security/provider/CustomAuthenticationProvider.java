package com.code4fun.book.config.security.provider;

import com.code4fun.book.config.security.authentication.CustomAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

// @Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

  @Value("${book.store.secret.key}")
  private String key;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    var ca = (CustomAuthentication) authentication;
    var headerKey = ca.key();

    if (key.equals(headerKey)) return new CustomAuthentication(true, null);

    throw new BadCredentialsException("BAD CREDENTIALS");
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return CustomAuthentication.class.equals(authentication);
  }
}
