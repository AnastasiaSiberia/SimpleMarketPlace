package com.uwplp.components.DAO;

import com.uwplp.components.models.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

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
}
