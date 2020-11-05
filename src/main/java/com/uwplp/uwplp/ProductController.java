package com.uwplp.uwplp;

import com.uwplp.components.ProductDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@RestController
@RequestMapping("/uwplp")
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private static ProductDAO productDAO;

    public ProductController() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContext.class);
        this.productDAO = (ProductDAO)context.getBean("productDAO");
    }

    @GetMapping
    public String readAll() {
        log.info("GET readAll");
        return productDAO.readAll().toString();
    }
    @GetMapping("/{id}")
    public String readByID(@PathVariable("id") Long id) {
        log.info("GET readByID");
        return productDAO.readByID(id).toString();
    }

    @PostMapping("/{id}")
    public String updateByID(@PathVariable("id") Long id, @RequestParam String name, @RequestParam Long views) {
        log.info("POST updateByID");
        log.debug("POST updateByID: {} {} {}",id, name, views);
        return productDAO.updateByID(id, name, views).toString();
    }

    @PostMapping()
    public String updateFew(@RequestParam("csvFile") MultipartFile csvFile) throws IOException {
        String csvString = new String(csvFile.getBytes(), StandardCharsets.UTF_8);
        log.info("POST updateFew");
        log.debug("POST updateFew:\n" + csvString);
        return productDAO.updateFew(csvString).toString();
    }

    @PostMapping("/statistic")
    public String getStatistic(@RequestParam("maxSize") Long maxSize) {
        return productDAO.getStatistic(maxSize).toString();
    }
}
