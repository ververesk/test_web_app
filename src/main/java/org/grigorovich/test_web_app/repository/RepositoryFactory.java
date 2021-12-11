package org.grigorovich.test_web_app.repository;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

@Slf4j
public class RepositoryFactory {

    private static RepositoryDatasource datasource;

    static {
        Properties appProperties = new Properties();
        try {
            appProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("app.properties"));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        try {
            datasource = RepositoryDatasource.getInstance(
                    appProperties.getProperty("postgres.driver"),
                    appProperties.getProperty("postgres.uri"),
                    appProperties.getProperty("postgres.user"),
                    appProperties.getProperty("postgres.password"));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    private RepositoryFactory() {
        //factory empty private
    }

    public static ExpenseRepository getExpenseRepository() {
        return ExpenseRepositoryPostgres.getInstance(datasource);
   }


}


