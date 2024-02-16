package com.code4fun.book.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book extends com.code4fun.book.model.Entity {

  private String name;
  private String reading;
  private String series;
  private Integer year;
  private Integer duration;
  private Integer quality;

  @ManyToMany(
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinTable(
      name = "books_authors",
      joinColumns = @JoinColumn(name = "book_id"),
      inverseJoinColumns = @JoinColumn(name = "author_id"))
  private Set<Author> authors = new HashSet<>();

  @ManyToMany(
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinTable(
      name = "books_categories",
      joinColumns = @JoinColumn(name = "book_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id"))
  private Set<Category> categories = new HashSet<>();

  public Book(
      String name, String reading, String series, Integer year, Integer duration, Integer quality) {
    this.name = name;
    this.reading = reading;
    this.series = series;
    this.year = year;
    this.duration = duration;
    this.quality = quality;
  }

  public void addAuthor(Author author) {
    this.authors.add(author);
    author.getBooks().add(this);
  }

  public void removeAuthor(Author author) {
    this.authors.remove(author);
    author.getBooks().remove(this);
  }

  public void addCategory(Category category) {
    this.categories.add(category);
    category.getBooks().add(this);
  }

  public void removeCategory(Category category) {
    this.categories.remove(category);
    category.getBooks().remove(this);
  }
}
