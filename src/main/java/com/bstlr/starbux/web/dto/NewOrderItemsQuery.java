package com.bstlr.starbux.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = NewOrderItemsQuery.NewOrderItemsQueryBuilder.class)
public class NewOrderItemsQuery {
    @NotNull
    @JsonProperty("customer_info")
    CustomerInfoDto customerInfo;
    @NotEmpty
    @Valid
    Set<DrinkWithToppingsDto> drinks;
}
