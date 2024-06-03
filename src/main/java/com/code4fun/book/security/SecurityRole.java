package com.code4fun.book.security;

import com.code4fun.book.model.Role;
import org.springframework.security.core.GrantedAuthority;

public record SecurityRole(Role role) implements GrantedAuthority {

  @Override
  public String getAuthority() {
    return role.name();
  }
}
