package com.uwplp;

import com.uwplp.components.DAO.OrdersDAO;
import com.uwplp.components.DAO.ProductReviewsDAO;
import com.uwplp.components.DAO.ProductsDAO;
import com.uwplp.components.DAO.UsersDAO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class UwplpApplicationTests {
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContext.class);
    private ProductsDAO productsDAO = (ProductsDAO) context.getBean("productsDAO");
    private ProductReviewsDAO productReviewsDAO = (ProductReviewsDAO) context.getBean("productReviewsDAO");
    private OrdersDAO ordersDAO = (OrdersDAO) context.getBean("ordersDAO");
    private UsersDAO usersDAO = (UsersDAO) context.getBean("usersDAO");

    @Nested
    class ContextLoads {
        @Test
        void contextLoads() {
            Assertions.assertNotNull(context);
        }
        @Test
        void dataSourceLoads() {
            DataSource dataSource = (DataSource)context.getBean("dataSource");
            Assertions.assertNotNull(dataSource);
        }
        @Test
        void productsDAOLoads() {
            ProductsDAO productsDAO = (ProductsDAO) context.getBean("productsDAO");
            Assertions.assertNotNull(productsDAO);
        }
        @Test
        void ordersDAOLoads() {
            OrdersDAO ordersDAO = (OrdersDAO) context.getBean("ordersDAO");
            Assertions.assertNotNull(ordersDAO);
        }
        @Test
        void productReviewsDAOLoads() {
            ProductReviewsDAO productReviewsDAO = (ProductReviewsDAO) context.getBean("productReviewsDAO");
            Assertions.assertNotNull(productReviewsDAO);
        }
        @Test
        void usersDAOLoads() {
            UsersDAO usersDAO = (UsersDAO) context.getBean("usersDAO");
            Assertions.assertNotNull(usersDAO);
        }
    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class DAOTesting {
        @Test
        public void readAllProductInfo() {
            Assertions.assertDoesNotThrow(productsDAO::readAllProductInfo);
        }

        @Test
        public void readByID() {
            Assertions.assertDoesNotThrow(() -> productsDAO.readByID(0L));
        }
        @Test
        public void readAllUserData() {
            Assertions.assertDoesNotThrow(() -> usersDAO.readAllUserData());
        }
        @Test
        public void getReviews() {
            Assertions.assertDoesNotThrow(() -> productReviewsDAO.getReviews(0L));
        }
        @Test
        public void getOrdersByUserID() {
            Assertions.assertDoesNotThrow(() -> ordersDAO.getOrdersByUserID(0L));
        }
    }
}
