package com.uwplp.components.authorizationaUnits;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.uwplp.components.models.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {

    private long id;

    private String username;

    private String password;

    private boolean enabled=true;
    private Collection<GrantedAuthority> authorities;

    private Date createdAt;

    private Date updatedAt;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    public User() {
    }

    public User(UserModel userModel) {
        this.id = userModel.getUserId();
        this.username = userModel.getUsername();
        this.password = userModel.getPassword();
        this.enabled = true;
        this.authorities = new ArrayList<>();
        this.authorities.add(new Authority(userModel));
    }

    public User(org.springframework.security.core.userdetails.User user) {
        this.id = 1; //!
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.enabled = true;
        this.authorities = user.getAuthorities();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return authorities;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return this.password;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return this.enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return this.enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return this.enabled;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return this.enabled;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}