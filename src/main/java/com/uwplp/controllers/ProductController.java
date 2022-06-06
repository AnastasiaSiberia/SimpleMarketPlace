package com.uwplp.controllers;

import com.uwplp.components.DAO.OrdersDAO;
import com.uwplp.components.DAO.ProductReviewsDAO;
import com.uwplp.components.DAO.UsersDAO;
import com.uwplp.components.models.OrderModel;
import com.uwplp.components.models.ProductModel;
import com.uwplp.components.DAO.ProductsDAO;
import com.uwplp.ApplicationContext;
import com.uwplp.components.models.ProductReviewModel;
import com.uwplp.components.models.UserModel;
import com.uwplp.components.requests.AddViewRequest;
import com.uwplp.services.CloudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductsDAO productsDAO;
    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private OrdersDAO ordersDAO;
    @Autowired
    private ProductReviewsDAO productReviewsDAO;
    @Autowired
    private CloudService cloudService;

    @GetMapping("/product_info")
    public ResponseEntity<?> readAllProductInfo() {
        log.info("the command \"readAll\" was gotten");
        List<ProductModel> body = productsDAO.readAllProductInfo();
        return ResponseEntity.ok()
                .body(body);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductModel> readByID(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productsDAO.readByID(id));
    }

    @PostMapping("/products/add_views")
    public ResponseEntity<?> addViews(@RequestBody List <AddViewRequest> requests) {
        requests.forEach(request -> productsDAO.addViews(request.getProductId(), request.getSize()));
        return ResponseEntity.ok("views was added");
    }

    @GetMapping("/products/{id}/reviews")
    public ResponseEntity<?> readReviewsByID(@PathVariable("id") Long id) {
        List<ProductReviewModel> reviews = productReviewsDAO.getReviews(id);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/products/{id}/review")
    public ResponseEntity<?> addProductReview(@PathVariable("id") Long productId, Principal user, @RequestBody ProductReviewModel model) {
        model.setUser_id(usersDAO.getByUsername(user.getName()).getUser_id());
        model.setProduct_id(productId);
        try {
            productReviewsDAO.addProductReview(model);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        productsDAO.addRating(productId, model.getReview_value());
        return ResponseEntity.ok("The review was added");
    }

    @PostMapping("/add_product")
    public ResponseEntity<?> addProduct(Principal user, @RequestBody ProductModel productModel) {
        log.debug("addProduct was called");
        productModel.setProduct_id(productsDAO.getNextId());
        Long vendorId = usersDAO.getByUsername(user.getName()).getUser_id();
        productModel.setVendor_id(vendorId);
        productsDAO.addProduct(productModel);
        return ResponseEntity.ok(productModel.getProduct_id());
    }
    @PostMapping("/product_image/upload/{product_id}")
    public ResponseEntity<String> uploadFile(Principal user,
                                   @PathVariable String product_id,
                                   @RequestBody MultipartFile file) throws IOException {
        cloudService.saveFile(user.getName(), product_id, file);
        return ResponseEntity.ok("The file was uploaded");
    }

    @GetMapping("/product_image/{vendor_name}/{product_id}")
    public ResponseEntity<?> getFile(@PathVariable String vendor_name, @PathVariable String product_id) throws FileNotFoundException {
        File file = cloudService.getFile(vendor_name, product_id);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.IMAGE_JPEG) //APPLICATION_OCTET_STREAM
                .body(resource);
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buy(Principal user, @RequestBody List<OrderModel> orders) {
        productsDAO.subtract(orders);
        Long userId = usersDAO.getByUsername(user.getName()).getUser_id();
        orders = orders.stream().peek((order) -> {
            order.setOrder_id(ordersDAO.getNextId());
            order.setUser_id(userId);
        }).collect(Collectors.toList());
        ordersDAO.addOrders(orders);
        return ResponseEntity.ok("The order was added");
    }

    @GetMapping("/products/{id}/disable")
    public ResponseEntity<String> disableProduct(Principal user, @PathVariable("id") Long productId) {
        if(usersDAO.getByUsername(user.getName()).getUser_role().equals(UserModel.Roles.ADMIN)||
                user.getName().equals(productsDAO.readByID(productId).getVendor_name())) {
            productsDAO.disableProduct(productId);
            return ResponseEntity.ok("The product was disabled");
        }
        else
            return ResponseEntity.badRequest().body("You don't have rights for this product");
    }
}
