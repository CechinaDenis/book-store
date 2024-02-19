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
@Table(name = "author")
public class Author extends com.code4fun.book.model.Entity {

  private String firstName;
  private String lastName;

  @ManyToMany(
      mappedBy = "authors",
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  private final Set<Book> books = new HashSet<>();

  public Author(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public void addBook(Book book) {
    this.books.add(book);
    book.getAuthors().add(this);
  }

  public void removeBook(Book book) {
    this.books.remove(book);
    book.getAuthors().remove(this);
  }
}
