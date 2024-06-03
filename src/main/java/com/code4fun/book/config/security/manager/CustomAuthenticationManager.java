package com.code4fun.book.config.security.manager;

import com.code4fun.book.config.security.provider.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

// @Component
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

  private final CustomAuthenticationProvider customProvider;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    if (customProvider.supports(authentication.getClass()))
      return customProvider.authenticate(authentication);

    throw new BadCredentialsException("Bad Credentials");
  }
}
