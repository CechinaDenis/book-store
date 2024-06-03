package com.code4fun.book.config.security.filter;

import com.code4fun.book.config.security.authentication.CustomAuthentication;
import com.code4fun.book.config.security.manager.CustomAuthenticationManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
// @Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {

  private final CustomAuthenticationManager customAuthenticationManager;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    var key = request.getHeader("key");
    var ca = new CustomAuthentication(false, key);

    var a = customAuthenticationManager.authenticate(ca);
    SecurityContextHolder.getContext().setAuthentication(a);

    filterChain.doFilter(request, response);
  }
}
