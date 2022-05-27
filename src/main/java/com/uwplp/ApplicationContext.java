package com.uwplp;

import com.uwplp.components.DAO.OrdersDAO;
import com.uwplp.components.DAO.ProductsDAO;
import com.uwplp.components.DAO.UsersDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@Configuration
@ComponentScan("com.uwplp.components")
public class ApplicationContext {
    private static final Logger log = LoggerFactory.getLogger(ApplicationContext.class);

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**").allowedOrigins("http://localhost:3000");
//            }
//        };
//    }
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/simplemarket");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        return dataSource;
    }

    @Bean
    public ProductsDAO productsDAO() {
        return new ProductsDAO(dataSource());
    }

    @Bean
    public UsersDAO usersDAO() {
        return new UsersDAO(dataSource());
    }

    @Bean
    public OrdersDAO ordersDAO() {
        return new OrdersDAO(dataSource());
    }
}
