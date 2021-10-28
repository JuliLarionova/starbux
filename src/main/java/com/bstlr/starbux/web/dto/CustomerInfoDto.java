package com.bstlr.starbux.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = CustomerInfoDto.CustomerInfoDtoBuilder.class)
public class CustomerInfoDto {
    @NotNull
    String name;
    @NotNull
    @JsonProperty("email_address")
    String emailAddress;
}
