package com.code4fun.book;

import com.code4fun.book.model.Book;
import com.code4fun.book.repositories.BookRepository;
import com.code4fun.book.services.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class BookApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BookRepository repository) {
        return args -> {
            final List<Book> books = List.of(
                    new Book("Римский Влад", 2018, 10, 56, "Дисгардиум"),
                    new Book("Римский Влад", 2019, 10, 56, "Дисгардиум"),
                    new Book("Римский Влад", 2020, 10, 56, "Дисгардиум"),
                    new Book("Римский Влад", 2021, 10, 56, "Дисгардиум")
            );

            repository.saveAll(books);
        };
    }
}
