package com.uwplp.components.requests;

public class ChangeRoleRequest {
    private Long userId;
    private String role;

    public Long getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }
}
