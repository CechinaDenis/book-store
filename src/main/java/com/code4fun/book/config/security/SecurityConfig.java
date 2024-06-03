package com.code4fun.book.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  //  private final CustomAuthenticationFilter customAuthenticationFilter;

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain webSecurityFilterChain(
      HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

    // Default authentication
    http.csrf(AbstractHttpConfigurer::disable)
        .httpBasic(Customizer.withDefaults())
        .authorizeHttpRequests(customizer -> customizer.anyRequest().authenticated());

    // Custom authentication
    //    http.addFilterBefore(customAuthenticationFilter,
    // UsernamePasswordAuthenticationFilter.class);

    // DISABLE SPRING SECURITY
    //    http.authorizeHttpRequests(
    //            c -> c.requestMatchers("/**").permitAll().anyRequest().authenticated())
    //        .csrf(AbstractHttpConfigurer::disable);

    return http.build();
  }
}
