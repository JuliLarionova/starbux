package com.bstlr.starbux.services;

import com.bstlr.starbux.entity.CustomerEntity;
import com.bstlr.starbux.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;

    public void createNewCustomer(String name) {
        repository.save(CustomerEntity.builder()
                .name(name)
                .build());
    }

    public CustomerEntity getCustomerOrCreate(String name, String emailAddress) {
        Optional<CustomerEntity> customer = repository.findByEmailAddress(emailAddress);
        if (customer.isEmpty()) {
            return repository.save(CustomerEntity.builder()
                    .name(name)
                    .emailAddress(emailAddress)
                    .build());
        }
        return customer.get();
    }
}
