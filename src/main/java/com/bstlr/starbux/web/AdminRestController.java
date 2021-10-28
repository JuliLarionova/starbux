package com.bstlr.starbux.web;

import com.bstlr.starbux.common.ClientException;
import com.bstlr.starbux.service.DrinkService;
import com.bstlr.starbux.service.MostUsedTopping;
import com.bstlr.starbux.service.ToppingService;
import com.bstlr.starbux.service.order.OrderItemToppingService;
import com.bstlr.starbux.service.order.OrderService;
import com.bstlr.starbux.web.dto.NewItemQuery;
import com.bstlr.starbux.web.dto.NewItemQuery.ItemType;
import com.bstlr.starbux.web.dto.OrderAmountPerCustomerDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AdminRestController {
    private final DrinkService drinkService;
    private final ToppingService toppingService;
    private final OrderItemToppingService orderItemToppingService;
    private final OrderService orderService;

    @GetMapping(value = "/report/orderAmountPerCustomer")
    public List<OrderAmountPerCustomerDto> getOrderAmountPerCustomer() {
        return orderService.getOrderAmountPerCustomer().stream()
                .map(el -> OrderAmountPerCustomerDto.builder()
                        .customerId(el.getCustomerId())
                        .customerName(el.getCustomerName())
                        .orderAmount(el.getOrderAmount().intValue())
                        .build())
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Get most used topping")
    @GetMapping(value = "/report/mostUsedTopping")
    public MostUsedTopping getMostUsedToppings() {
        log.trace("A TRACE Message");
        log.debug("A DEBUG Message");
        log.info("An INFO Message");
        log.warn("A WARN Message");
        log.error("An ERROR Message");
        return orderItemToppingService.getMostUsedTopping();
    }

    @ApiOperation(value = "Create new product item")
    @PutMapping(value = "/{itemType}/create")
    public UUID createItem(@PathVariable ItemType itemType, @RequestBody @Valid NewItemQuery newItem) {
        if (itemType == ItemType.DRINK) {
            return drinkService.createNewDrink(newItem.getName(), newItem.getPrice(), newItem.getCurrency());
        } else if (itemType == ItemType.TOPPING) {
            return toppingService.createNewTopping(newItem.getName(), newItem.getPrice(), newItem.getCurrency());
        }
        throw new ClientException(String.format("Cannot create item with type %s", itemType));
    }

    @ApiOperation(value = "Remove product item")
    @DeleteMapping(value = "/{itemType}/remove")
    public void remove(@PathVariable ItemType itemType, @RequestParam UUID id) {
        if (itemType == ItemType.DRINK) {
            drinkService.removeDrinkById(id);
        } else if (itemType == ItemType.TOPPING) {
            toppingService.removeToppingById(id);
        }
    }
}