package com.code4fun.book.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_role")
public class UserRole extends com.code4fun.book.model.Entity {

  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "userRole")
  private Set<User> users;
}
