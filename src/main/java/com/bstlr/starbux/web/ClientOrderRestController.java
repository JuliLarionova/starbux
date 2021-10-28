package com.bstlr.starbux.web;

import com.bstlr.starbux.entity.CustomerEntity;
import com.bstlr.starbux.service.CustomerService;
import com.bstlr.starbux.service.DiscountService;
import com.bstlr.starbux.service.order.OrderItemDrinkService;
import com.bstlr.starbux.service.order.OrderService;
import com.bstlr.starbux.web.converter.OrderItemsToCartDtoConverter;
import com.bstlr.starbux.web.dto.CartDto;
import com.bstlr.starbux.web.dto.CustomerInfoDto;
import com.bstlr.starbux.web.dto.NewOrderItemsQuery;
import com.bstlr.starbux.web.dto.OrderCostDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/order",  produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ClientOrderRestController {
    private final DiscountService discountService;
    private final CustomerService customerService;
    private final OrderService orderService;
    private final OrderItemDrinkService orderItemDrinkService;
    private final OrderItemsToCartDtoConverter cartDtoConverter;

    @ApiOperation(value = "Add new drink with toppings", response = CartDto.class)
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK")
    })
    @PostMapping(value = "/newItems")
    public CartDto newItems(@Valid @RequestBody NewOrderItemsQuery newItemsToCreate) {
        CustomerInfoDto customerInfo = newItemsToCreate.getCustomerInfo();
        CustomerEntity customer = customerService.getCustomerOrCreate(customerInfo.getName(), customerInfo.getEmailAddress());
        UUID orderId = orderService.addNewOrderItems(customer.getId(), newItemsToCreate.getDrinks());

        return cartDtoConverter.convert(orderItemDrinkService.findOrderItemsByOrderId(orderId));
    }

    @ApiOperation(value = "Get order info by id", response = CartDto.class)
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK")
    })
    @GetMapping(value = "/{orderId}")
    public CartDto order(@PathVariable UUID orderId) {
        return cartDtoConverter.convert(orderItemDrinkService.findOrderItemsByOrderId(orderId));
    }

    @ApiOperation(value = "Place order and get order cost with discount", response = OrderCostDto.class)
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK")
    })
    @PostMapping(value = "/{orderId}/place")
    public OrderCostDto placeOrder(@PathVariable UUID orderId) {
        orderService.placeOrder(orderId);
        return discountService.setDiscountAndGetTotalCost(orderId);
    }

    @ApiOperation(value = "Get id of active order for customer by email address", response = UUID.class)
    @GetMapping(value = "/active")
    public UUID orderId(String emailAddress) {
        return orderService.getActiveOrderByCustomerEmail(emailAddress);
    }
}
