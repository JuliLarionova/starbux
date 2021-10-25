package com.bstlr.starbux.common;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class TestLiquibaseConfig {
    private static final String PATH_TO_CHANGE_LOG = "classpath:db/changelog/db-changelog.xml";

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(PATH_TO_CHANGE_LOG);
        liquibase.setContexts("test");
        return liquibase;
    }
}
