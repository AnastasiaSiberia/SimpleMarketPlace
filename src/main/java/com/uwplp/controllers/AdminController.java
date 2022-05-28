package com.uwplp.controllers;

import com.uwplp.components.DAO.UsersDAO;
import com.uwplp.components.models.UserModel;
import com.uwplp.components.requests.ChangeRoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UsersDAO usersDAO;

    @PostMapping("/change_role")
    public ResponseEntity<?> changeRole(@RequestBody ChangeRoleRequest request) {
        usersDAO.changeRole(request.getUserId(), request.getRole());
        return ResponseEntity.ok("role was changed");
    }

     @GetMapping("/users")
    public ResponseEntity readAllUsers() {
        List<UserModel> body = usersDAO.readAllUserData();
        return ResponseEntity.ok()
                .body(body);
    }
}
