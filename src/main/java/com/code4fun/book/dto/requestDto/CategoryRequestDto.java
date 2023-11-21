package com.code4fun.book.dto.requestDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryRequestDto {
  private String uuid;
  private String name;
}
