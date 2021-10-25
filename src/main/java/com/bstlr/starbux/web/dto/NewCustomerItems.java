package com.bstlr.starbux.web.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder(toBuilder = true)
public class NewCustomerItems {
    @NotNull
    CustomerInfo customerInfo;
    @NotEmpty
    @Valid
    Set<DrinkWithToppings> drinks;
}
