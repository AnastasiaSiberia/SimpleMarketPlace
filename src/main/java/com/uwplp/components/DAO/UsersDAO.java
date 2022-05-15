package com.uwplp.components.DAO;

import com.uwplp.components.models.ProductModel;
import com.uwplp.components.models.UserModel;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO {
    private final JdbcTemplate jdbcTemplate;
    private final String tableName = "users";

    public UsersDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<UserModel> readAllUserData() {
        return new ArrayList<>(jdbcTemplate.query(
                "SELECT user_id, username, user_role FROM " + tableName,
                (res, rowNum) -> new UserModel(res)
        ));
    }
}
