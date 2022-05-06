package com.code4fun.book.dto.responseDto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookResponseDto {
    private Long id;
    private String name;
    private List<String> authorNames;
}
