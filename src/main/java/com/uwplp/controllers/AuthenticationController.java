package com.uwplp.controllers;

import com.uwplp.components.DAO.UsersDAO;
import com.uwplp.components.authorizationaUnits.User;
import com.uwplp.components.requests.AuthenticationRequest;
import com.uwplp.components.requests.RegistrationRequest;
import com.uwplp.components.responses.LoginResponse;
import com.uwplp.components.responses.UserInfo;
import com.uwplp.config.JWTTokenHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTTokenHelper jWTTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UsersDAO usersDAO;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String password = decodeBase64(authenticationRequest.getPassword());
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user=(User)authentication.getPrincipal();
        String jwtToken=jWTTokenHelper.generateToken(user.getUsername());
        LoginResponse response=new LoginResponse();
        response.setToken(jwtToken);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/auth/userinfo")
    public ResponseEntity<?> getUserInfo(Principal user) {
        User userObj = (User) userDetailsService.loadUserByUsername(user.getName());
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(userObj.getUserName());
        userInfo.setRoles(userObj.getAuthorities().toArray());
        return ResponseEntity.ok(userInfo);
    }

    @PostMapping("/auth/registration")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        request.setPassword(decodeBase64(request.getPassword()));
        try {
            usersDAO.addUser(request);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.ok("The user was added");
    }

    @PostMapping("/auth/authorize2")
    public ResponseEntity <String> authorize2(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(decodeBase64(request.getPassword()));
    }

    private String decodeBase64(String password) {
        return new String(Base64.getDecoder().decode(password));
    }
}