package com.uwplp.controllers;

import com.uwplp.components.DAO.OrdersDAO;
import com.uwplp.components.DAO.UsersDAO;
import com.uwplp.components.models.OrderModel;
import com.uwplp.components.models.ProductModel;
import com.uwplp.components.DAO.ProductsDAO;
import com.uwplp.components.models.UserModel;
import com.uwplp.ApplicationContext;
import com.uwplp.services.CloudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private static ProductsDAO productsDAO;
    private static UsersDAO usersDAO;
    private static OrdersDAO ordersDAO;

    @Autowired
    private CloudService cloudService;

    public ProductController() {
        try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContext.class)) {
            productsDAO = (ProductsDAO)context.getBean("productsDAO");
            usersDAO = (UsersDAO)context.getBean("usersDAO");
            ordersDAO = (OrdersDAO)context.getBean("ordersDAO");
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

    @PostMapping("/add_product")
    public ResponseEntity addProduct(Principal user, @RequestBody ProductModel productModel) {
        log.debug("addProduct was called");
        productModel.setProduct_id(productsDAO.getNextId());
        Long vendorId = usersDAO.getByUsername(user.getName()).getUserId();
        productModel.setVendor_id(vendorId);
        productsDAO.addProduct(productModel);
        return ResponseEntity.ok(productModel.getProduct_id());
    }
    @PostMapping("/product_image/upload/{product_id}")
    public ResponseEntity loadFile(Principal user,
                                   @PathVariable String product_id,
                                   @RequestBody MultipartFile file) throws IOException {
        cloudService.saveFile(user.getName(), product_id, file);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/product_image/{vendor_name}/{product_id}")
    public ResponseEntity getFile(@PathVariable String vendor_name, @PathVariable String product_id) throws FileNotFoundException {
        File file = cloudService.getFile(vendor_name, product_id);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.IMAGE_JPEG) //APPLICATION_OCTET_STREAM
                .body(resource);
    }

    @PostMapping("/buy")
    public ResponseEntity buy(Principal user, @RequestBody List<OrderModel> orders) {
        productsDAO.subtract(orders);
        Long userId = usersDAO.getByUsername(user.getName()).getUserId();
        orders = orders.stream().map((order) -> {
            order.setOrder_id(ordersDAO.getNextId());
            order.setUser_id(userId);
            return order;
        }).collect(Collectors.toList());
        ordersDAO.addOrders(orders);
        return ResponseEntity.ok("The order was added");
    }
}
