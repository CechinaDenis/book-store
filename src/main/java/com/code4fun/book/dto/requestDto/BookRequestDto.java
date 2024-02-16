package com.code4fun.book.dto.requestDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookRequestDto {
  @NotNull private Integer year;
  @NotNull private Integer duration;
  @NotNull private Integer quality;
  @NotBlank private String name;
  @NotBlank private String reading;
  @NotBlank private String series;
}
