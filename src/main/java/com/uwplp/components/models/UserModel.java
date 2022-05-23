package com.uwplp.components.models;

import com.uwplp.utils.ResultSetColumnChecker;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.ResultSet;
import java.sql.SQLException;

@Entity
public class UserModel {
    @Id
    private Long userId;
    private String username;
    private String password;
    private Roles userRole;

    public UserModel() {}

    public UserModel(ResultSet rs) throws SQLException {
        this.userId = rs.getLong("user_id");
        this.username = rs.getString("username");
        this.password = rs.getString("password");
        this.userRole = Roles.valueOf(rs.getString("user_role"));
    }

    public enum Roles {
        ADMIN,
        USER,
        VENDOR
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userRole=" + userRole +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Roles getUserRole() {
        return userRole;
    }

    public void setUserRole(Roles userRole) {
        this.userRole = userRole;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
