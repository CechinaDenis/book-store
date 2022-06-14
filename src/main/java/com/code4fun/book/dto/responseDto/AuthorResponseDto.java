package com.code4fun.book.dto.responseDto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AuthorResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Set<Long> bookIds;
}
