package com.bstlr.starbux.web;

import com.bstlr.starbux.web.dto.ItemType;
import com.bstlr.starbux.services.DrinkService;
import com.bstlr.starbux.services.ToppingService;
import com.bstlr.starbux.web.dto.NewItemQuery;
import com.bstlr.starbux.web.dto.OrderAmountPerCustomerDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminRestController {
    private final DrinkService drinkService;
    private final ToppingService toppingService;

    @GetMapping(value = "/report/orderAmountPerCustomer")
    public List<OrderAmountPerCustomerDto> getOrderAmountPerCustomer(@RequestBody UUID customerId) {
        return Collections.emptyList();
    }

    @ApiOperation(value = "Create new product item")
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK")
    })
    @PutMapping(value = "/{itemType}/create")
    public void createItem(@PathVariable ItemType itemType, @RequestBody @Valid NewItemQuery newItem) {
        if (itemType == ItemType.DRINK) {
            drinkService.createNewDrink(newItem.getName(), newItem.getPrice(), newItem.getCurrency());
        } else if(itemType == ItemType.TOPPING) {
            toppingService.createNewDrink(newItem.getName(), newItem.getPrice(), newItem.getCurrency());
        }
    }

    @ApiOperation(value = "Remove product item")
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK")
    })
    @DeleteMapping(value = "/{itemType}/remove")
    public void remove(@PathVariable ItemType itemType, @RequestParam UUID id) {
        if (itemType == ItemType.DRINK) {
            drinkService.removeDrinkById(id);
        } else if(itemType == ItemType.TOPPING) {
            toppingService.removeDrinkById(id);
        }
    }
}