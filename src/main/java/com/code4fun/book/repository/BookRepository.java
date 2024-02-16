package com.code4fun.book.repository;

import com.code4fun.book.model.Book;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
  Optional<Book> findBookByName(String bookName);
}
