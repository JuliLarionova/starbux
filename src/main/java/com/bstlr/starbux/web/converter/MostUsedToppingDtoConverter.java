package com.bstlr.starbux.web.converter;

import com.bstlr.starbux.service.MostUsedTopping;
import com.bstlr.starbux.web.dto.MostUsedToppingDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MostUsedToppingDtoConverter {

    MostUsedToppingDto convert(MostUsedTopping dto);
}
