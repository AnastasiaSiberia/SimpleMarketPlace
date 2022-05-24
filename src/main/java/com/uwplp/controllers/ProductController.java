package com.uwplp.controllers;

import com.uwplp.components.DAO.UsersDAO;
import com.uwplp.components.models.ProductModel;
import com.uwplp.components.DAO.ProductsDAO;
import com.uwplp.components.models.UserModel;
import com.uwplp.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private static ProductsDAO productsDAO;
    private static UsersDAO usersDAO;
    public ProductController() {
        try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContext.class)) {
            productsDAO = (ProductsDAO)context.getBean("productsDAO");
            usersDAO = (UsersDAO)context.getBean("usersDAO");
        }
    }

    @Deprecated
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

    @GetMapping("/admin")
    public ResponseEntity adminHello() {
        return new ResponseEntity("hello admin", HttpStatus.OK);
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

    @GetMapping("/admin/users")
    public ResponseEntity readAllUsers() {
        List <UserModel> body = usersDAO.readAllUserData();
        log.debug(body.toString());
        return ResponseEntity.ok()
                .body(body);
    }
/*
public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @GetMapping("/add_product")
    public ResponseEntity addProduct(String productName, String productDescription) {
        log.debug("addProduct was called");
        Long vendorId = usersDAO.getByUsername(getCurrentUsername()).getUserId();
        ProductModel productModel = new ProductModel();
        productModel.setProduct_name(productName);
        productModel.setProduct_description(productDescription);
        productModel.setVendor_id(vendorId);
        productsDAO.addProduct(productModel);
        return ResponseEntity.ok().body(null);
    }*/
}
