package com.uwplp.components.DAO;

import com.uwplp.components.models.UserModel;
import com.uwplp.components.requests.RegistrationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsersDAO extends DAO{

    public Logger log = LoggerFactory.getLogger(UsersDAO.class);
    public static String TABLE_NAME = "users";

    public UsersDAO(DataSource dataSource) {
        super(dataSource, TABLE_NAME, "user_sequence");
    }

    public UserModel getByUsername(String username) {
        String sqlCommand = String.format("SELECT * FROM %s WHERE username = '%s'",
                TABLE_NAME, username);
        List <UserModel> res = jdbcTemplate.query(sqlCommand, (rs, rowNum) -> new UserModel(rs));
        if(res.isEmpty()) {
            throw new NullPointerException("The user was not found");
        }
        return res.get(0);
    }

    public String getUsernameByUserID(long userId) {
        List <UserModel> res = jdbcTemplate.query(
                "SELECT user_id, username FROM " + TABLE_NAME + " where user_id = " + userId,
                (rs, rowNum) -> new UserModel(rs)
        );
        if(res.isEmpty()) {
            return null;
        }
        return res.get(0).getUsername();
    }

    public List<UserModel> readAllUserData() {
        return new ArrayList<>(jdbcTemplate.query(
                "SELECT user_id, username, user_role, user_email FROM " + TABLE_NAME,
                (res, rowNum) -> new UserModel(res)
        ));
    }

    public Long getNextUserId() {
        try {
            List<Map<String, Object>> maxIds = jdbcTemplate.queryForList("SELECT max(user_id) from " + TABLE_NAME);
            return Long.parseLong(maxIds.get(0).get("max").toString()) + 1;
        } catch(NullPointerException exception) {
            return 0L;
        }
    }
    public void addUser(RegistrationRequest request) {
        Long userId = getNextUserId();
        String encodedPassword = new BCryptPasswordEncoder().encode(request.getPassword());
        String sqlCommand = String.format("INSERT INTO %s VALUES(%d, '%s', '%s', '%s', '%s')",
                TABLE_NAME,
                userId,
                request.getUsername(),
                "USER",
                encodedPassword,
                request.getEmail()
                );
        jdbcTemplate.execute(sqlCommand);
    }

    public void changeRole(Long userId, String role) {
        String sqlCommand = String.format("UPDATE %s SET user_role = '%s' WHERE user_id = %d",
                TABLE_NAME, role, userId);
        jdbcTemplate.execute(sqlCommand);
    }
}
