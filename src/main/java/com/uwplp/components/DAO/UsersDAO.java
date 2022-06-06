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
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
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


    public void addUser(RegistrationRequest request) {
        validate(request);
        Long userId = getNextId();
        String encodedPassword = encoder.encode(request.getPassword());
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

    private void validate(RegistrationRequest request) {
        if(request.getUsername().length() < 3) {
            throw new IllegalArgumentException("Имя пользователя должно быть длиннее 3 букв");
        }
        if(request.getPassword().length() < 3) {
            throw new IllegalArgumentException("Пароль пользователя должен быть длиннее 3 букв");
        }
        try {
            getByUsername(request.getUsername());
            throw new IllegalArgumentException("Такой никнейм уже существует");
        } catch (NullPointerException ignored) {}
    }

    public void changeRole(Long userId, String role) {
        String sqlCommand = String.format("UPDATE %s SET user_role = '%s' WHERE user_id = %d",
                TABLE_NAME, role, userId);
        jdbcTemplate.execute(sqlCommand);
    }
}
