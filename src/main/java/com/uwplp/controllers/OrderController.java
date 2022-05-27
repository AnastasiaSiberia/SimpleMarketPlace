package com.uwplp.controllers;

import com.uwplp.ApplicationContext;
import com.uwplp.components.DAO.OrdersDAO;
import com.uwplp.components.DAO.ProductsDAO;
import com.uwplp.components.DAO.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@CrossOrigin
public class OrderController {
    @Autowired
    private OrdersDAO ordersDAO;
    @Autowired
    private UsersDAO usersDAO;

//    public OrderController() {
//        try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContext.class)) {
//            usersDAO = (UsersDAO)context.getBean("usersDAO");
//            ordersDAO = (OrdersDAO)context.getBean("ordersDAO");
//        }
//    }

    @GetMapping("/orders")
    public ResponseEntity getOrders(Principal user) {
        Long user_id = usersDAO.getByUsername(user.getName()).getUserId();
        return ResponseEntity.ok(ordersDAO.getOrdersByUserID(user_id));
    }
}
