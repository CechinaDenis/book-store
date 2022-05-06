package com.code4fun.book.dto.responseDto;

import com.code4fun.book.model.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthorResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private List<String> bookNames;

}
