package com.code4fun.book.dto;

import com.code4fun.book.dto.requestDto.AuthorRequestDto;
import com.code4fun.book.dto.requestDto.BookRequestDto;
import com.code4fun.book.dto.requestDto.CategoryRequestDto;
import com.code4fun.book.dto.responseDto.AuthorResponseDto;
import com.code4fun.book.dto.responseDto.BookResponseDto;
import com.code4fun.book.dto.responseDto.CategoryResponseDto;
import com.code4fun.book.model.Author;
import com.code4fun.book.model.Book;
import com.code4fun.book.model.Category;

import java.util.List;
import java.util.stream.Collectors;

public class mapper {

    public static BookResponseDto bookToBookResponseDto(Book book) {
        var authorIds = book.getAuthors()
                .stream()
                .map(Author::getId)
                .collect(Collectors.toSet());

        var categoryIds = book.getCategories()
                .stream()
                .map(Category::getId)
                .collect(Collectors.toSet());

        return BookResponseDto.builder()
                .id(book.getId())
                .name(book.getName())
                .reading(book.getReading())
                .series(book.getSeries())
                .year(book.getYear())
                .duration(book.getDuration())
                .quality(book.getQuality())
                .authorIds(authorIds)
                .categoryIds(categoryIds)
                .build();
    }

    public static List<BookResponseDto> booksToBookResponseDtos(List<Book> books) {
        return books.stream().map(mapper::bookToBookResponseDto).collect(Collectors.toList());
    }

    public static Book bookRequestDtoToBook(BookRequestDto requestDto) {
        return new Book(
                requestDto.getName(),
                requestDto.getReading(),
                requestDto.getSeries(),
                requestDto.getYear(),
                requestDto.getDuration(),
                requestDto.getQuality());
    }

    public static AuthorResponseDto authorToAuthorResponseDto(Author author) {
        var bookIds = author.getBooks().stream().map(Book::getId).collect(Collectors.toSet());

        return AuthorResponseDto.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .bookIds(bookIds)
                .build();
    }

    public static List<AuthorResponseDto> authorsToAuthorResponseDtos(List<Author> authors) {
        return authors.stream().map(mapper::authorToAuthorResponseDto).collect(Collectors.toList());
    }

    public static Author authorRequestDtoToAuthor(AuthorRequestDto requestDto) {
        return new Author(requestDto.getFirstName(), requestDto.getLastName());
    }

    public static CategoryResponseDto categoryToCategoryResponseDto(Category category) {
        var bookIds = category.getBooks().stream().map(Book::getId).collect(Collectors.toSet());

        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .bookIds(bookIds)
                .build();
    }

    public static List<CategoryResponseDto> categoriesToCategoryResponseDtos(List<Category> categories) {
        return categories.stream().map(mapper::categoryToCategoryResponseDto).collect(Collectors.toList());
    }

    public static Category categoryRequestDtoToCategory(CategoryRequestDto requestDto) {
        return new Category(requestDto.getName());
    }
}
