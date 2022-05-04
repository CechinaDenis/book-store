package com.code4fun.book.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "authors_books",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();
    //    private List<Category> categories;
    private String reading;
    private Integer year;
    private Integer duration;
    private Integer quality;
    private String series;

    public Book(String reading, Integer year, Integer duration, Integer quality, String series) {
        this.reading = reading;
        this.year = year;
        this.duration = duration;
        this.quality = quality;
        this.series = series;
    }
}
