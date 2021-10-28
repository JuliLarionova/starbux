package com.bstlr.starbux.service;

import com.bstlr.starbux.BaseIT;
import com.bstlr.starbux.entity.CustomerEntity;
import com.bstlr.starbux.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.bstlr.starbux.TestDataFactory.CUSTOMER_EMAIL;
import static com.bstlr.starbux.TestDataFactory.CUSTOMER_NAME;

public class CustomerServiceTest extends BaseIT {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void createsNewCustomer() {
        //when
        CustomerEntity createdCustomer = customerService.getCustomerOrCreate(CUSTOMER_NAME, CUSTOMER_EMAIL);
        //then
        CustomerEntity byId = customerRepository.getById(createdCustomer.getId());
        Assert.assertNotNull(byId);
        Assertions.assertEquals(createdCustomer.getEmailAddress(), CUSTOMER_EMAIL);
        Assertions.assertEquals(createdCustomer.getName(), CUSTOMER_NAME);
    }
}