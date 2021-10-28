package com.bstlr.starbux.service;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.UUID;

@AllArgsConstructor
@Value
public class MostUsedTopping {
    Long amount;
    UUID id;
    String name;
}
