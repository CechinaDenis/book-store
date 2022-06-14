package com.code4fun.book.dto.requestDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookRequestDto {
    private Long id;
    private Integer year;
    private Integer duration;
    private Integer quality;
    private String name;
    private String reading;
    private String series;
}
