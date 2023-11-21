package com.code4fun.book.dto.responseDto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CategoryResponseDto {
  private String uuid;
  private String name;
  private Set<String> bookIds;
}
