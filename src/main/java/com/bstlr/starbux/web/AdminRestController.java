package com.bstlr.starbux.web;

import com.bstlr.starbux.common.ClientException;
import com.bstlr.starbux.service.DrinkService;
import com.bstlr.starbux.service.ToppingService;
import com.bstlr.starbux.service.order.OrderItemToppingService;
import com.bstlr.starbux.service.order.OrderService;
import com.bstlr.starbux.web.converter.MostUsedToppingDtoConverter;
import com.bstlr.starbux.web.converter.OrderAmountPerCustomerDtoConverter;
import com.bstlr.starbux.web.converter.ProductDtoConverter;
import com.bstlr.starbux.web.dto.MostUsedToppingDto;
import com.bstlr.starbux.web.dto.OrderAmountPerCustomerDto;
import com.bstlr.starbux.web.dto.ProductDto;
import com.bstlr.starbux.web.dto.ProductQuery;
import com.bstlr.starbux.web.dto.ProductQuery.ProductType;
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
    private final ProductDtoConverter productDtoConverter;

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

    @ApiOperation(value = "Add new product, returns id of created product")
    @PutMapping(value = "product/{productType}/create")
    public UUID createProduct(@PathVariable ProductType productType, @RequestBody @Valid ProductQuery newProduct) {
        if (productType == ProductType.DRINK) {
            return drinkService.createNewDrink(newProduct.getName(), newProduct.getPrice(), newProduct.getCurrency());
        } else if (productType == ProductType.TOPPING) {
            return toppingService.createNewTopping(newProduct.getName(), newProduct.getPrice(), newProduct.getCurrency());
        }
        throw new ClientException(String.format("Cannot create product with type %s", productType));
    }

    @ApiOperation(value = "Remove product")
    @DeleteMapping(value = "product/{productType}/remove")
    public void removeProduct(@PathVariable ProductType productType, @RequestParam UUID id) {
        if (productType == ProductType.DRINK) {
            drinkService.removeDrinkById(id);
        } else if (productType == ProductType.TOPPING) {
            toppingService.removeToppingById(id);
        }
    }

    @ApiOperation(value = "Update product by id")
    @PostMapping(value = "product/{productType}/{productId}/update")
    public void updateProduct(@PathVariable ProductType productType, @PathVariable UUID productId,
                              @RequestBody @Valid ProductQuery newProduct) {
        if (productType == ProductType.DRINK) {
            drinkService.updateById(productId, newProduct.getName(), newProduct.getPrice(), newProduct.getCurrency());
        } else if (productType == ProductType.TOPPING) {
            toppingService.updateById(productId, newProduct.getName(), newProduct.getPrice(), newProduct.getCurrency());
        } else {
            throw new ClientException(String.format("Cannot find product with type %s. Cannot update product with id %s",
                    productType, productId));
        }
    }

    @ApiOperation(value = "Get info about products. It is used to retrieve info (for example: id) for further work")
    @GetMapping(value = "product/{productType}")
    public List<ProductDto> getAllProducts(@PathVariable ProductType productType) {
        if (productType == ProductType.DRINK) {
            return productDtoConverter.convertDrinks(drinkService.getAll());
        } else if (productType == ProductType.TOPPING) {
            return productDtoConverter.convertToppings(toppingService.getAll());
        }
        throw new ClientException(String.format("Cannot find product with type %s.", productType));
    }
}