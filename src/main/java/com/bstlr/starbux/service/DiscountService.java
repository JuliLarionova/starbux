package com.bstlr.starbux.service;

import com.bstlr.starbux.common.properties.DiscountProperties;
import com.bstlr.starbux.entity.order.OrderItemDrinkEntity;
import com.bstlr.starbux.repository.OrderItemDrinkRepository;
import com.bstlr.starbux.web.dto.OrderCostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscountService {
    private final OrderItemDrinkRepository orderItemDrinkRepository;
    private final DiscountProperties discountProperties;
    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    public OrderCostDto setDiscountAndGetTotalCost(UUID orderId) {
        List<OrderItemDrinkEntity> orderItemsDrink = orderItemDrinkRepository.findAllByOrderId(orderId);
        BigDecimal totalOrderCost = orderItemsDrink.stream()
                .map(OrderItemDrinkEntity::getTotalCost)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        BigDecimal rateDiscountAmountForCart = getRateDiscountAmountForCart(totalOrderCost);
        BigDecimal discountByLowestAmountForCart = getDiscountByLowestAmountForCart(orderItemsDrink);
        if (rateDiscountAmountForCart.equals(BigDecimal.ZERO) && discountByLowestAmountForCart.equals(BigDecimal.ZERO)) {
            return OrderCostDto.builder()
                    .totalCost(totalOrderCost)
                    .discount(BigDecimal.ZERO)
                    .build();
        }

        BigDecimal discount;
        if (!rateDiscountAmountForCart.equals(BigDecimal.ZERO) && !discountByLowestAmountForCart.equals(BigDecimal.ZERO)) {
            discount = rateDiscountAmountForCart.min(discountByLowestAmountForCart);
        } else {
            discount = rateDiscountAmountForCart.equals(BigDecimal.ZERO) ? discountByLowestAmountForCart
                    : rateDiscountAmountForCart;
        }
        return OrderCostDto.builder()
                .totalCost(totalOrderCost)
                .discount(discount)
                .build();
    }

    private BigDecimal getRateDiscountAmountForCart(BigDecimal totalOrderCost) {
        if (totalOrderCost.compareTo(discountProperties.getCostForRateDiscount()) < 1) {
            return BigDecimal.ZERO;
        }

        BigDecimal percentageDiscount = BigDecimal.valueOf(discountProperties.getRateDiscount());
        return totalOrderCost.multiply(percentageDiscount).divide(ONE_HUNDRED,2, RoundingMode.HALF_UP);
    }

    private BigDecimal getDiscountByLowestAmountForCart(List<OrderItemDrinkEntity> orderItemsDrink) {
        Integer drinkSize = orderItemsDrink.size();
        if (drinkSize < discountProperties.getDrinkAmountForDrinkDiscount()) {
            return BigDecimal.ZERO;
        }

        return orderItemsDrink.stream()
                .map(OrderItemDrinkEntity::getTotalCost)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

}
