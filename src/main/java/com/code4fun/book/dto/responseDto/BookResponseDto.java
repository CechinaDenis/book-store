package com.code4fun.book.dto.responseDto;

import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponseDto {
  private String uuid;
  private String name;
  private String reading;
  private String series;
  private Integer year;
  private Integer duration;
  private Integer quality;
  private Set<String> authorIds;
  private Set<String> categoryIds;
}
