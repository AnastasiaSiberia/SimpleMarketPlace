package com.uwplp.uwplp;

import com.uwplp.components.ProductDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.uwplp.components")
public class ApplicationContext {
    private static final Logger log = LoggerFactory.getLogger(ApplicationContext.class);
    @Bean
    public DataSource dataSource() {
        log.info("dataSource is CREATING");
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url("jdbc:h2:~/db/myDB");
        //dataSourceBuilder.username("SA");
        //dataSourceBuilder.password("");
        return dataSourceBuilder.build();
    }
    @Bean
    public ProductDAO productDAO() {
        log.info("productDAO is CREATING");
        return new ProductDAO(dataSource());
    }
}
