package com.code4fun.book.model;

import javax.persistence.Entity;
import javax.persistence.Table;
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
