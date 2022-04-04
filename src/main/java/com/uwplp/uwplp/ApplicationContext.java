package com.uwplp.uwplp;

import com.uwplp.components.ProductDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;


@Configuration
@ComponentScan("com.uwplp.components")
public class ApplicationContext {
    private static final Logger log = LoggerFactory.getLogger(ApplicationContext.class);

    @PostConstruct
    public void Init() {
        log.info("####################################APLICATIONCONTEXTRESTART##############################################");
    }

    @Bean
    public DataSource productDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/simplemarket");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        return dataSource;
    }

    @Bean
    public ProductDAO productDAO() {
        log.info("productDAO is CREATING");
        return new ProductDAO(productDataSource());
    }
}
