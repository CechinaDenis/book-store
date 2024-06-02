package com.code4fun.book.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role extends com.code4fun.book.model.Entity {

  @Enumerated()
  private UserRoles userRoles;

  public enum UserRoles {
    ADMIN,
    USER,
    UNKNOWN
  }
}