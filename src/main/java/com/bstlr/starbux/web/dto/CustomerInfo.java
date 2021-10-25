package com.bstlr.starbux.web.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder(toBuilder = true)
public class CustomerInfo {
    @NotNull
    String name;
    @NotNull
    String emailAddress;
}
