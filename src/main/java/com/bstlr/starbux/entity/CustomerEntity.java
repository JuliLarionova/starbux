package com.bstlr.starbux.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
public class CustomerEntity {

    @Id
    @Builder.Default
    UUID id = UUID.randomUUID();

    @NonNull
    String name;

    @NonNull
    @Column(name = "email_address")
    String emailAddress;
}
