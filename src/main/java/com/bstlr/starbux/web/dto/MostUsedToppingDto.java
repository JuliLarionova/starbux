package com.bstlr.starbux.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonDeserialize(builder = MostUsedToppingDto.MostUsedToppingDtoBuilder.class)
public class MostUsedToppingDto {
    Long amount;
    UUID id;
    String name;
}
