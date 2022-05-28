package com.uwplp.components.DAO;

import com.uwplp.components.models.UserModel;
import com.uwplp.components.requests.RegistrationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsersDAO {
    private final JdbcTemplate jdbcTemplate;
    public static final String TABLENAME = "users";

    public Logger log = LoggerFactory.getLogger(UsersDAO.class);

    public UsersDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public UserModel getByUsername(String username) {

        List <UserModel> res = jdbcTemplate.query(
                "SELECT * FROM " + TABLENAME + " where username = \'" + username + "\'",
                (rs, rowNum) -> new UserModel(rs)
        );
        if(res.isEmpty()) {
            return null;
        }
        return res.get(0);
    }

    public String getUsernameByUserID(long userId) {
        List <UserModel> res = jdbcTemplate.query(
                "SELECT user_id, username FROM " + TABLENAME + " where user_id = " + userId,
                (rs, rowNum) -> new UserModel(rs)
        );
        if(res.isEmpty()) {
            return null;
        }
        return res.get(0).getUsername();
    }

    public List<UserModel> readAllUserData() {
        return new ArrayList<>(jdbcTemplate.query(
                "SELECT user_id, username, user_role FROM " + TABLENAME,
                (res, rowNum) -> new UserModel(res)
        ));
    }

    public Long getNextUserId() {
        try {
            List<Map<String, Object>> maxIds = jdbcTemplate.queryForList("SELECT max(user_id) from " + TABLENAME);
            return Long.parseLong(maxIds.get(0).get("max").toString()) + 1;
        } catch(NullPointerException exception) {
            return 0L;
        }
    }
    public void addUser(RegistrationRequest request) {
        Long userId = getNextUserId();
        String encodedPassword = new BCryptPasswordEncoder().encode(request.getPassword());
        String sqlCommand = String.format("INSERT INTO %s VALUES(%d, '%s', '%s', '%s')",
                TABLENAME,
                userId,
                request.getUsername(),
                "USER",
                encodedPassword
                );
        try {
            jdbcTemplate.execute(sqlCommand);
        } catch (Exception ex) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }
    }

    public void changeRole(Long userId, String role) {
        String sqlCommand = String.format("UPDATE %s SET user_role = '%s' WHERE user_id = %d",
                TABLENAME, role, userId);
        jdbcTemplate.execute(sqlCommand);
    }
}
