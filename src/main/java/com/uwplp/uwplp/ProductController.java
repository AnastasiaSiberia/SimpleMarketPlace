package com.uwplp.uwplp;

import com.uwplp.components.ProductModel;
import com.uwplp.components.ProductsDAO;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@RestController
@RequestMapping("/uwplp")
@CrossOrigin
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private static ProductsDAO productsDAO;

    public ProductController() {
        try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContext.class)) {
            productsDAO = (ProductsDAO)context.getBean("productDAO");
        }
    }

    @GetMapping("/products")
    public String readAllProducts() {
        log.info("the command \"readAll\" was gotten");
        return productsDAO.readAll().toString();
    }

    @GetMapping("/product_info")
    public ResponseEntity readAllProductInfo() {
        log.info("the command \"readAll\" was gotten");
        List<ProductModel> body = productsDAO.readAllProductInfo();
        return ResponseEntity.ok()
                .body(body);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity readByID(@PathVariable("id") Long id) {
        return new ResponseEntity(productsDAO.readByID(id), HttpStatus.OK);
    }
    @GetMapping("/products/{id}/reviews")
    public ResponseEntity readReviewsByID(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
                .body("nothing yet");
    }
    /*@PostMapping("/products/{id}")
    public String updateProductByID(@PathVariable("id") Long id,
                             @RequestParam("name") String productName,
                             @RequestParam(value = "views", defaultValue = "0") Long views) {
        return productDAO.updateByID(id, name, 0L).toString();
    }*/

    @GetMapping("/delete")
    public String deleteAll() {
        return productsDAO.deleteAll().toString();
    }

}
