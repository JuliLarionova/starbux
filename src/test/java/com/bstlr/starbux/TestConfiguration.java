package com.bstlr.starbux;

import com.bstlr.starbux.common.properties.DiscountProperties;
import com.bstlr.starbux.config.JpaConfig;
import com.bstlr.starbux.config.TestLiquibaseConfig;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {
        "com.bstlr.starbux",
        "com.bstlr.starbux.service"
})
@SpringBootConfiguration
@EnableJpaRepositories(basePackages = {"com.bstlr.starbux.repository"})
@Import({TestLiquibaseConfig.class, JpaConfig.class})
@EnableConfigurationProperties(value = {DiscountProperties.class})
public class TestConfiguration {
}
