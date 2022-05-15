package com.uwplp.uwplp;

import com.uwplp.components.DAO.ProductsDAO;
import org.json.JSONArray;
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
    private static final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(com.uwplp.uwplp.ApplicationContext.class);
    private static final ProductsDAO productsDAO = (ProductsDAO) context.getBean("productDAO");
    private static final Logger log = LoggerFactory.getLogger(UwplpApplicationTests.class);

    public UwplpApplicationTests() {
        log.info("#########################TESTING####################################");
    }

    @Nested
    class ContextLoads {
        @Test
        void contextLoads() {
            Assertions.assertNotNull(context);
        }
        @Test
        void dataSourceLoads() {
            DataSource dataSource = (DataSource)context.getBean("productDataSource");
            Assertions.assertNotNull(dataSource);
        }
        @Test
        void daoLoads() {
            ProductsDAO productsDAO = (ProductsDAO) context.getBean("productDAO");
            Assertions.assertNotNull(productsDAO);
        }
    }

    /*@BeforeAll
    public static void fillDb() throws Exception {
        File file = ResourceUtils.getFile("classpath:test.csv");
        String csvString = new String(Files.readAllBytes(file.toPath()));
        productDAO.updateFew(csvString);
    }*/

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class DAOTesting {
        @Test
        @Order(1)
        public void readAll() {
            JSONArray jsonArray = productsDAO.readAll();
            System.out.println(jsonArray);
            Assertions.assertTrue(jsonArray.toString().length() > 0);
        }

        @Test
        @Order(2)
        public void readByID() {
            var list = productsDAO.readByID(0L);
            System.out.println(list);
            Assertions.assertTrue(list.toString().length() > 0);
        }

        @Test
        @Order(3)
        public void readAllProductInfo() {
            var list = productsDAO.readAllProductInfo();
            System.out.println(list);
            Assertions.assertTrue(list.toString().length() > 0);
        }
    }
}
