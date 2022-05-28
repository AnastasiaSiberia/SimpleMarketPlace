package com.uwplp.components.models;

import com.uwplp.utils.ResultSetColumnChecker;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.ResultSet;
import java.sql.SQLException;

@Entity
public class UserModel {
    @Id
    private Long user_id;
    private String username;
    private String password;
    private Roles user_role;

    public UserModel() {}

    public UserModel(ResultSet rs) throws SQLException {
        ResultSetColumnChecker checker = new ResultSetColumnChecker(rs);
        if(checker.hasColumn("user_id"))
            this.user_id = rs.getLong("user_id");
        if(checker.hasColumn("username"))
            this.username = rs.getString("username");
        if(checker.hasColumn("password"))
            this.password = rs.getString("password");
        if(checker.hasColumn("user_role"))
            this.user_role = Roles.valueOf(rs.getString("user_role"));
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
                "userId=" + user_id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userRole=" + user_role +
                '}';
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long userId) {
        this.user_id = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Roles getUser_role() {
        return user_role;
    }

    public void setUser_role(Roles userRole) {
        this.user_role = userRole;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
