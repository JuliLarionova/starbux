package com.bstlr.starbux.common;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Errors {
    String message;
    String code;
}
