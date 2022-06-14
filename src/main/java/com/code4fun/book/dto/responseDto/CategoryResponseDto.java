package com.code4fun.book.dto.responseDto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CategoryResponseDto {
    private Long id;
    private String name;
    private Set<Long> bookIds;
}
