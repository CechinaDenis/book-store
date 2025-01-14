package com.code4fun.book.unit.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.code4fun.book.mapper.AuthorMapper;
import com.code4fun.book.mapper.AuthorMapperImpl;
import com.code4fun.book.mapper.EntityToEntityIdsMapperImpl;
import com.code4fun.book.model.Author;
import com.code4fun.book.model.Book;
import com.code4fun.book.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class AuthorMapperTests {

  private final AuthorMapper authorMapper = new AuthorMapperImpl(new EntityToEntityIdsMapperImpl());

  @Test
  public void authorToAuthorResponseTest() {
    var author = new Author("author name", "author last name");
    ReflectionTestUtils.setField(author, "id", "author-1");
    var book = new Book("testBook", "reader", "series", 2000, 180, 192);
    ReflectionTestUtils.setField(book, "id", "book-1");
    book.addAuthor(author);
    var category = new Category("fantasy");
    ReflectionTestUtils.setField(category, "id", "category-1");
    book.addCategory(category);
    author.addBook(book);

    var authorResponse = authorMapper.map(author);
    assertThat(authorResponse).isNotNull().hasNoNullFieldsOrProperties();
    assertThat(authorResponse.bookIds()).hasSize(1);
  }
}
