package com.uwplp.authorizationUnit;

public class User {
    private final Long ID;
    private final String username;
    private final UserRole role;

    public User(Long ID, String username, UserRole role) {
        this.ID = ID;
        this.username = username;
        this.role = role;
    }

    public Long getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public UserRole getRole() {
        return role;
    }

    public static enum UserRole {
        ADMIN,
        VENDOR,
        CUSTOMER
    }
}
