package com.code4fun.book.repository;

import com.code4fun.book.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {

  @Query("""
      SELECT u FROM User u WHERE u.username = :username
      """)
  Optional<User> findUserByUsername(String username);
}
