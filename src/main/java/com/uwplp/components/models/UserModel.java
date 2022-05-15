package com.uwplp.components.models;

import com.uwplp.utils.ResultSetColumnChecker;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    private final String DEFAULT_USERNAME = "NOT FOUND";
    private final int userId;
    private final String username;
    private final Roles userRole;

    public UserModel(int userId, String username, Roles role) {
        this.userId = userId;
        this.username = username;
        this.userRole = role;
    }

    public UserModel(ResultSet rs) throws SQLException {
        ResultSetColumnChecker checker = new ResultSetColumnChecker(rs);
        this.userId = rs.getInt("user_id");
        this.username = checker.hasColumn("username") ? rs.getString("username") : DEFAULT_USERNAME;
        this.userRole = checker.hasColumn("user_role") ? Roles.valueOf(rs.getString("user_role").trim()) : Roles.USER;
    }

    public enum Roles {
        ADMIN,
        USER,
        VENDOR
    }
}
