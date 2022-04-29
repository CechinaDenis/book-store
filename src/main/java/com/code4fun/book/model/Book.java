package com.code4fun.book.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private List<Author> authors;
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
