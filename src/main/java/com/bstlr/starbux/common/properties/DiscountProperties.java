package com.bstlr.starbux.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Validated
@ConfigurationProperties(prefix = "app.discount")
public class DiscountProperties {

    /**
     * Amount of rate discount
     */
    @NotNull
    private Integer rateDiscount;

    /**
     * Amount of order which is eligible for rate discount
     */
    @NotNull
    private BigDecimal costForRateDiscount;

    /**
     * Amount of order which is eligible for drink discount
     */
    @NotNull
    private Integer drinkAmountForDrinkDiscount;
}