package com.uwplp.uwplp;

import com.uwplp.components.ProductDAO;
import org.aspectj.lang.annotation.After;
import org.json.JSONArray;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.File;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class UwplpApplicationTests {
    private static final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(com.uwplp.uwplp.ApplicationContext.class);
    private static final ProductDAO productDAO = (ProductDAO) context.getBean("productDAO");
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
            DataSource dataSource = (DataSource)context.getBean("dataSource");
            Assertions.assertNotNull(dataSource);
        }
        @Test
        void daoLoads() {
            ProductDAO productDAO = (ProductDAO) context.getBean("productDAO");
            Assertions.assertNotNull(productDAO);
        }
    }

    @BeforeAll
    public static void fillDb() throws Exception {
        File file = ResourceUtils.getFile("classpath:test.csv");
        String csvString = new String(Files.readAllBytes(file.toPath()));
        productDAO.updateFew(csvString);
    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class DAOTesting {
        @Test
        @Order(1)
        public void readAll() {
            JSONArray jsonArray = productDAO.readAll();
            Assertions.assertEquals("[{\"name\":\"shoes\",\"id\":1,\"views\":0},{\"name\":\"boots\",\"id\":2,\"views\":0},{\"name\":\"blouse\",\"id\":3,\"views\":0},{\"name\":\"skirt\",\"id\":4,\"views\":0},{\"name\":\"lipstick\",\"id\":5,\"views\":0}]",jsonArray.toString());
        }

        @Test
        @Order(2)
        public void readByID() {
            JSONArray jsonArray = productDAO.readByID(2L);
            Assertions.assertEquals("[{\"name\":\"boots\",\"id\":2,\"views\":1}]", jsonArray.toString());
        }

        @Test
        @Order(3)
        public void updateFew() throws Exception {
            File file = ResourceUtils.getFile("classpath:test2.csv");
            String csvString = new String(Files.readAllBytes(file.toPath()));
            productDAO.updateFew(csvString);
            Thread.sleep(1000);
            JSONArray jsonArray = productDAO.readAll();
            Assertions.assertEquals("[{\"name\":\"shoes Jiccardo\",\"id\":1,\"views\":0},{\"name\":\"boots Riccardo\",\"id\":2,\"views\":1},{\"name\":\"blouse Dolca&Gubanno\",\"id\":3,\"views\":0},{\"name\":\"skirt Gussi\",\"id\":4,\"views\":0},{\"name\":\"lipstick Maybeenwill New York\",\"id\":5,\"views\":0},{\"name\":\"new shirt EXCLUSIVE\",\"id\":6,\"views\":0}]"
                    ,jsonArray.toString());
        }
    }


    @AfterAll
    public static void resetDb() {
        productDAO.deleteAll();
    }
    
}
