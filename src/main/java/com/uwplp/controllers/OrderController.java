package com.uwplp.controllers;

import com.uwplp.components.DAO.OrdersDAO;
import com.uwplp.components.DAO.UsersDAO;
import com.uwplp.components.models.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class OrderController {
    @Autowired
    private OrdersDAO ordersDAO;
    @Autowired
    private UsersDAO usersDAO;
    @GetMapping("/orders")
    public ResponseEntity<List<OrderModel> > getOrders(Principal user) {
        Long user_id = usersDAO.getByUsername(user.getName()).getUser_id();
        return ResponseEntity.ok(ordersDAO.getOrdersByUserID(user_id));
    }
}
