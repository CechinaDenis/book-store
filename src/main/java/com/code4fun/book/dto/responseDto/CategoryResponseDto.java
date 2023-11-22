package com.code4fun.book.dto.responseDto;

import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponseDto {
  private String uuid;
  private String name;
  private Set<String> bookIds;
}
