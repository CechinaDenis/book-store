package com.code4fun.book.dto.responseDto;

import java.util.Set;
import lombok.Data;

@Data
public class CategoryResponseDto {
  private String id;
  private String name;
  private Set<String> bookIds;
}
