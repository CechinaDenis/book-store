package com.code4fun.book.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends com.code4fun.book.model.Entity {

  private String username;
  private String password;
}
