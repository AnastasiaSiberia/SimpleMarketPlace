package com.uwplp.controllers;

import com.uwplp.components.DAO.UsersDAO;
import com.uwplp.components.models.UserModel;
import com.uwplp.components.requests.ChangeRoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UsersDAO usersDAO;

    @PostMapping("/change_role")
    public ResponseEntity<?> changeRole(Principal user, @RequestBody ChangeRoleRequest request) {
        if(request.getUserId().equals(usersDAO.getByUsername(user.getName()).getUser_id())) {
            return ResponseEntity.badRequest().body("You're trying to change your role");
        }
        usersDAO.changeRole(request.getUserId(), request.getRole());
        return ResponseEntity.ok("role was changed");
    }

     @GetMapping("/users")
    public ResponseEntity<List<UserModel>> readAllUsers() {
        List<UserModel> body = usersDAO.readAllUserData();
        return ResponseEntity.ok()
                .body(body);
    }
}
