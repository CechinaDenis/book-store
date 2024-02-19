package com.code4fun.book.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category extends com.code4fun.book.model.Entity {

  private String name;

  @ManyToMany(
      mappedBy = "categories",
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  private Set<Book> books = new HashSet<>();

  public Category(String name) {
    this.name = name;
  }

  public void addBook(Book book) {
    this.books.add(book);
    book.getCategories().add(this);
  }

  public void removeBook(Book book) {
    this.books.remove(book);
    book.getCategories().remove(this);
  }
}
