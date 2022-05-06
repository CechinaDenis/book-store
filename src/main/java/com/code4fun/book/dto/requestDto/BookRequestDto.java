package com.code4fun.book.dto.requestDto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BookRequestDto {
    private String name;
    private List<Long> authorsIds;

}
