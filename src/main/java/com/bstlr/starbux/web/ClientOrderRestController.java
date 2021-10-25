package com.bstlr.starbux.web;

import com.bstlr.starbux.entity.CustomerEntity;
import com.bstlr.starbux.services.CustomerService;
import com.bstlr.starbux.services.DiscountService;
import com.bstlr.starbux.services.OrderService;
import com.bstlr.starbux.web.converter.OrderItemsToCartDtoConverter;
import com.bstlr.starbux.web.dto.CartDto;
import com.bstlr.starbux.web.dto.CustomerInfo;
import com.bstlr.starbux.web.dto.NewCustomerItems;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

@Slf4j
//@ControllerAdvice
@RestController
@RequestMapping(value = "/order")
@RequiredArgsConstructor
public class ClientOrderRestController {
    private final DiscountService discountService;
    private final CustomerService customerService;
    private final OrderService orderService;

    private final OrderItemsToCartDtoConverter cartDtoConverter;
/*
    @ApiOperation(value = "Place order", response = BigDecimal.class)
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK")
    })
    @PostMapping(value = "/new")
    public BigDecimal newOrder(@RequestBody @Valid OrderQuery order) {
        CustomerInfo customer = order.getCustomerInfo();
        customerService.checkCustomerExistenceOrCreate(customer.getName(), customer.getEmailAddress());
        return discountService.getTotalOrderAmount(order.getDrinkWithToppings());
    }*/

    @ApiOperation(value = "Add new drink with toppings", response = CartDto.class)
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK")
    })
    @PostMapping(value = "/newItem")
    public CartDto newItems(@Valid @RequestBody NewCustomerItems newItemsToCreate) {
        CustomerInfo customerInfo = newItemsToCreate.getCustomerInfo();
        CustomerEntity customer = customerService.getCustomerOrCreate(customerInfo.getName(), customerInfo.getEmailAddress());
        UUID orderId = orderService.addNewOrderItems(customer.getId(), newItemsToCreate.getDrinks());

        return cartDtoConverter.convert(orderService.findOrderInfoById(orderId));
    }

    @ApiOperation(value = "Get order info by id", response = CartDto.class)
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK")
    })
    @PostMapping(value = "/{orderId}")
    public CartDto order(@PathVariable UUID orderId) {
        return cartDtoConverter.convert(orderService.findOrderInfoById(orderId));
    }
}
