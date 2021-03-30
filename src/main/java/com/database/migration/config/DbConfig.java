package com.database.migration.config;

import com.database.migration.constants.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DbConfig {


    @Autowired
    private Environment environment;

    @Bean(name = CommonConstants.SOURCE_DATASOURCE_BEAN_NAME)
    @Primary
    public DataSource sourceDatabaseDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(environment.getProperty("datasource.url"));
        dataSourceBuilder.driverClassName(environment.getProperty("datasource.driver-class"));
        dataSourceBuilder.username(environment.getProperty("datasource.username"));
        dataSourceBuilder.password(environment.getProperty("datasource.password"));
        return dataSourceBuilder.build();
    }

    @Bean(name = CommonConstants.SOURCE_JDBC_TEMPLATE_BEAN_NAME)
    @Primary
    public JdbcTemplate sourceJdbcTemplate(@Qualifier(CommonConstants.SOURCE_DATASOURCE_BEAN_NAME) DataSource sourceDatabaseDataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(sourceDatabaseDataSource);
        return jdbcTemplate;
    }

    @Bean(name = CommonConstants.DESTINATION_DATASOURCE_BEAN_NAME)
    public DataSource destinationDatabaseDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(environment.getProperty("datadestination.url"));
        dataSourceBuilder.driverClassName(environment.getProperty("datadestination.driver-class"));
        dataSourceBuilder.username(environment.getProperty("datadestination.username"));
        dataSourceBuilder.password(environment.getProperty("datadestination.password"));
        return dataSourceBuilder.build();
    }

    @Bean(name = CommonConstants.DESTINATION_JDBC_TEMPLATE_BEAN_NAME)
    public JdbcTemplate destinationJdbcTemplate(@Qualifier(CommonConstants.DESTINATION_DATASOURCE_BEAN_NAME) DataSource destinationDatabaseDataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(destinationDatabaseDataSource);
        return jdbcTemplate;
    }


}
