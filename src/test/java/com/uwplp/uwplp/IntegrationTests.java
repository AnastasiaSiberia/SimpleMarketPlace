package com.uwplp.uwplp;

import com.uwplp.components.ProductDAO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    private static final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(com.uwplp.uwplp.ApplicationContext.class);
    private static final ProductDAO productDAO = (ProductDAO) context.getBean("productDAO");
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    private static final Logger log = LoggerFactory.getLogger(IntegrationTests.class);

    public IntegrationTests() {
        log.info("#########################INTEGRATION_TESTING####################################");
    }


    @Test
    @Order(1)
    public void readByIdOnlyWeb() throws Exception {
        mockMvc.perform(get("/uwplp/3")).andExpect(status().isOk())
                .andExpect(content().string(containsString("[{\"name\":\"blouse\",\"id\":3,\"views\":1}]")));
    }
    @Test
    public void updateByIdOnlyWeb() throws Exception {
        mockMvc.perform(post("/uwplp/4").param("name","skirt MUSA").param("views", "0")).andExpect(status().isOk());
    }
    /*@Test
    public void updateFewOnlyWeb() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("name", "skirt MUSA");
        mockMvc.perform(post("/uwplp/").contentType(MediaType.APPLICATION_FORM_URLENCODED).content(map);.requestAttr("csvFile", ResourceUtils.getFile("classpath:test2.csv"))).andExpect(status().isOk());
    }*/

    @BeforeAll
    public static void fillDb() throws Exception {
        File file = ResourceUtils.getFile("classpath:test.csv");
        String csvString = new String(Files.readAllBytes(file.toPath()));
        productDAO.updateFew(csvString);
    }

    @Test
    @Order(1)
    public void readById() {
        String result = new String(restTemplate.getForObject("http://localhost:" + port + "uwplp/1", String.class).getBytes(), StandardCharsets.UTF_8);
        log.debug("readById give " + result);
        Assertions.assertEquals("[{\"name\":\"shoes\",\"id\":1,\"views\":1}]", result);
    }
    @Test
    @Order(2)
    public void updateById() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("name", "skirt MUSA");
        HttpEntity<MultiValueMap<String, String> > request = new HttpEntity<>(map, httpHeaders);
        restTemplate.postForObject("http://localhost:" + port + "uwplp/4", request, String.class);
        String result = new String(restTemplate.getForObject("http://localhost:" + port + "uwplp/4", String.class).getBytes(), StandardCharsets.UTF_8);
        Assertions.assertEquals("[{\"name\":\"skirt MUSA\",\"id\":4,\"views\":1}]", result);
    }

    @Test
    @Order(3)
    public void updateFew() throws Exception {
        ResultMatcher ok = MockMvcResultMatchers.status().isOk();
        File file = ResourceUtils.getFile("classpath:test2.csv");
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/uwplp/").file("csvFile", Files.readAllBytes(file.toPath()));
        this.mockMvc.perform(builder).andExpect(ok)
                .andDo(MockMvcResultHandlers.print());
        String result = new String(restTemplate.getForObject("http://localhost:" + port + "uwplp/", String.class).getBytes(), StandardCharsets.UTF_8);
        log.debug("updateFew " + result);
        Assertions.assertEquals("[{\"name\":\"shoes Jiccardo\",\"id\":1,\"views\":1},{\"name\":\"boots Riccardo\",\"id\":2,\"views\":0},{\"name\":\"blouse Dolca&Gubanno\",\"id\":3,\"views\":1},{\"name\":\"skirt Gussi\",\"id\":4,\"views\":1},{\"name\":\"lipstick Maybeenwill New York\",\"id\":5,\"views\":0},{\"name\":\"new shirt EXCLUSIVE\",\"id\":6,\"views\":0}]", result);

    }
    @AfterAll
    public static void resetDb() {
        productDAO.deleteAll();
    }

}
