package com.uwplp.uwplp;

import com.uwplp.components.ProductDAO;
import com.uwplp.components.ProductModel;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;
import java.util.Scanner;

@RestController
@RequestMapping("/uwplp")
//@EnableWebSecurity
public class ProductController/* extends WebSecurityConfigurerAdapter*/ {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private static ProductDAO productDAO;

    public ProductController() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContext.class);
        this.productDAO = (ProductDAO)context.getBean("productDAO");
    }

    @GetMapping("/products")
    public String readAllProducts() {
        log.info("the command \"readAll\" was gotten");
        return productDAO.readAll().toString();
    }
    @GetMapping("/products/{id}")
    public String readByID(@PathVariable("id") Long id) {
        return productDAO.readByID(id).toString();
    }

    /*@PostMapping("/products/{id}")
    public String updateProductByID(@PathVariable("id") Long id,
                             @RequestParam("name") String productName,
                             @RequestParam(value = "views", defaultValue = "0") Long views) {
        return productDAO.updateByID(id, name, 0L).toString();
    }

    @PostMapping()
    public String updateFew(@RequestParam("csvFile") MultipartFile csvFile) throws IOException {
        String csvString = new String(csvFile.getBytes(), StandardCharsets.UTF_8);
        return productDAO.updateFew(csvString).toString();
    }*/

    @PostMapping("/statistic")
    public String getStatistic(@RequestParam("maxSize") Long maxSize) {
        return productDAO.getStatistic(maxSize).toString();
    }

    @GetMapping("/delete")
    public String deleteAll() {
        return productDAO.deleteAll().toString();
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
