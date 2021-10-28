package com.bstlr.starbux.service.order;

import com.bstlr.starbux.repository.OrderItemToppingRepository;
import com.bstlr.starbux.service.MostUsedTopping;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderItemToppingService {
    private final OrderItemToppingRepository repository;

    public MostUsedTopping getMostUsedTopping() {
        Pageable pageRequest = PageRequest.of(0, 1, Sort.by("sum_"));
        Page<MostUsedTopping> topping = repository.findTop1MostUsedTopping(pageRequest);
        if (topping.isEmpty()) return null;
        return topping.getContent().get(0);
        //return repository.getMostUsedTopping().isEmpty() ? null : repository.getMostUsedTopping().get(0);
    }
}
