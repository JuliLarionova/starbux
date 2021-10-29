package com.bstlr.starbux.web.converter;

import com.bstlr.starbux.entity.DrinkEntity;
import com.bstlr.starbux.entity.ToppingEntity;
import com.bstlr.starbux.web.dto.ProductDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductDtoConverter {

    ProductDto convert(DrinkEntity dto);

    default List<ProductDto> convertDrinks(List<DrinkEntity> dtos) {
        return dtos.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    ProductDto convert(ToppingEntity dto);

    default List<ProductDto> convertToppings(List<ToppingEntity> dtos) {
        return dtos.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
