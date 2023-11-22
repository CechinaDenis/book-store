package com.code4fun.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookApplication.class, args);
  }

  // @Bean
  // CommandLineRunner commandLineRunner(BookRepository repository) {
  // return args -> {
  // final Category fantasy = new Category("Fantasy");
  // final Category drama = new Category("Drama");
  // final Category litRPG = new Category("LitRPG");
  //
  // final Author author1 = new Author("Данияр", "Сугралинов");
  // final Author author2 = new Author("Денис", "Владимиров");
  //
  // final Book book1 = new Book("Book 1", "Римский Влад", "Дисгардиум", 2018, 10,
  // 56);
  // book1.addCategory(fantasy);
  // book1.addCategory(litRPG);
  // book1.addAuthor(author1);
  //
  // repository.save(book1);
  //
  // final Book book2 = new Book("Book 2", "Римский Влад", "Дисгардиум", 2019, 11,
  // 57);
  //
  // book2.addAuthor(author2);
  // book2.addCategory(drama);
  // repository.save(book2);
  //// final Book book3 = new Book("Book 3", "Римский Влад", 2020, 10, 56,
  // "Дисгардиум");
  //// final Book book4 = new Book("Book 4", "Римский Влад", 2021, 10, 56,
  // "Дисгардиум");
  // };
  // }
}
