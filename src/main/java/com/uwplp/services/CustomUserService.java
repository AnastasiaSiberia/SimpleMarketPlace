package com.uwplp.services;

import com.uwplp.components.DAO.UsersDAO;
import com.uwplp.components.authorizationaUnits.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserService implements UserDetailsService {
    @Autowired
    UsersDAO usersDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User(usersDAO.getByUsername(username));
        if(user == null) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
        return user;
    }
}
