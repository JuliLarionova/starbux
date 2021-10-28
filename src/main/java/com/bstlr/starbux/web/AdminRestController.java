package com.bstlr.starbux.web;

import com.bstlr.starbux.common.ClientException;
import com.bstlr.starbux.service.DrinkService;
import com.bstlr.starbux.service.ToppingService;
import com.bstlr.starbux.service.order.OrderItemToppingService;
import com.bstlr.starbux.service.order.OrderService;
import com.bstlr.starbux.web.converter.MostUsedToppingDtoConverter;
import com.bstlr.starbux.web.converter.OrderAmountPerCustomerDtoConverter;
import com.bstlr.starbux.web.dto.MostUsedToppingDto;
import com.bstlr.starbux.web.dto.NewProductQuery;
import com.bstlr.starbux.web.dto.NewProductQuery.ProductType;
import com.bstlr.starbux.web.dto.OrderAmountPerCustomerDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AdminRestController {
    private final DrinkService drinkService;
    private final ToppingService toppingService;
    private final OrderItemToppingService orderItemToppingService;
    private final OrderService orderService;

    private final OrderAmountPerCustomerDtoConverter orderAmountPerCustomerDtoConverter;
    private final MostUsedToppingDtoConverter mostUsedToppingDtoConverter;

    @ApiOperation(value = "Get order amount per customer")
    @GetMapping(value = "/report/orderAmountPerCustomer")
    public List<OrderAmountPerCustomerDto> getOrderAmountPerCustomer() {
        return orderAmountPerCustomerDtoConverter.convert(orderService.getOrderAmountPerCustomer());
    }

    @ApiOperation(value = "Get most used topping")
    @GetMapping(value = "/report/mostUsedTopping")
    public MostUsedToppingDto getMostUsedToppings() {
        return mostUsedToppingDtoConverter.convert(orderItemToppingService.getMostUsedTopping());
    }

    @ApiOperation(value = "Add new product")
    @PutMapping(value = "/{productType}/create")
    public UUID createProduct(@PathVariable ProductType productType, @RequestBody @Valid NewProductQuery newItem) {
        if (productType == ProductType.DRINK) {
            return drinkService.createNewDrink(newItem.getName(), newItem.getPrice(), newItem.getCurrency());
        } else if (productType == ProductType.TOPPING) {
            return toppingService.createNewTopping(newItem.getName(), newItem.getPrice(), newItem.getCurrency());
        }
        throw new ClientException(String.format("Cannot create item with type %s", productType));
    }

    @ApiOperation(value = "Remove product")
    @DeleteMapping(value = "/{productType}/remove")
    public void removeProduct(@PathVariable ProductType productType, @RequestParam UUID id) {
        if (productType == ProductType.DRINK) {
            drinkService.removeDrinkById(id);
        } else if (productType == ProductType.TOPPING) {
            toppingService.removeToppingById(id);
        }
    }
}