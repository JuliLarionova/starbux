package com.bstlr.starbux.web.converter;

import com.bstlr.starbux.service.order.OrdersPerCustomer;
import com.bstlr.starbux.web.dto.OrderAmountPerCustomerDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface OrderAmountPerCustomerDtoConverter {

    OrderAmountPerCustomerDto convert(OrdersPerCustomer dto);

    default List<OrderAmountPerCustomerDto> convert(List<OrdersPerCustomer> dtos) {
        return dtos.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
