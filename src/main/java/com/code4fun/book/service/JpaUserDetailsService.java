package com.code4fun.book.service;

import com.code4fun.book.repository.UserRepository;
import com.code4fun.book.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class JpaUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = userRepository.findUserByUsername(username);
    return user.map(SecurityUser::new)
        .orElseThrow(
            () -> {
              log.warn("No user found with such username: '{}'", username);
              return new UsernameNotFoundException("The username or password is incorrect");
            });
  }
}
