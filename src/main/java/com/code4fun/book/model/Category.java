package com.code4fun.book.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Category extends com.code4fun.book.model.Entity {

  private String name;

  @ManyToMany(mappedBy = "categories")
  private Set<Book> books = new HashSet<>();

  public Category(String name) {
    this.name = name;
  }
}
