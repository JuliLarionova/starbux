package com.bstlr.starbux;

import com.bstlr.starbux.entity.CustomerEntity;
import com.bstlr.starbux.entity.CustomerEntity.CustomerEntityBuilder;

import java.util.UUID;

import static java.util.UUID.fromString;

public class TestDataFactory {

    public static final String CUSTOMER_NAME = "Bob";
    public static final String CUSTOMER_EMAIL = "email";
    public static final UUID CUSTOMER_ID = fromString("ee3e4567-e91b-12d3-1111-5566f2222222");

    public static CustomerEntityBuilder getCustomer() {
        return CustomerEntity.builder()
                .id(CUSTOMER_ID)
                .name(CUSTOMER_NAME)
                .emailAddress(CUSTOMER_EMAIL);
    }
}
