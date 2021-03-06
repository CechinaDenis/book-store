package com.code4fun.book.dto.responseDto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class BookResponseDto {
    private Long id;
    private String name;
    private String reading;
    private String series;
    private Integer year;
    private Integer duration;
    private Integer quality;
    private Set<Long> authorIds;
    private Set<Long> categoryIds;
}
