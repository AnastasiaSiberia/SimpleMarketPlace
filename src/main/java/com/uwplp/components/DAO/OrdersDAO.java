package com.uwplp.components.DAO;

import com.uwplp.components.models.OrderModel;
import com.uwplp.components.models.ProductModel;
import com.uwplp.components.models.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrdersDAO {

    private final JdbcTemplate jdbcTemplate;
    public static final String TABLENAME = "orders";
    public static final String SEQUENCE_NAME = "order_sequence";
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public OrdersDAO (DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addOrders(List<OrderModel> orders) {
        orders.forEach(order -> {
            String command = String.format(
                    "INSERT INTO %s  values(%d, %d, %d, %d, %d, TO_DATE('%s', 'dd/MM/YYYY'))",
                    TABLENAME,
                    order.getOrder_id(),
                    order.getUser_id(),
                    order.getProduct_id(),
                    order.getOrder_price(),
                    order.getOrder_size(),
                    formatter.format(order.getOrder_time())
            );
            jdbcTemplate.execute(command);
        });
    }

    public Long getNextId() {
        String sqlCommand = String.format("SELECT nextval('%s')", SEQUENCE_NAME);
        return jdbcTemplate.query(sqlCommand, (rs, rowNum) -> rs.getLong("nextval")).get(0);
    }

    public List<OrderModel> getOrdersByUserID(long userId) {
        try {
            String sqlCommand = String.format("SELECT * from %s WHERE user_id = %d", TABLENAME, userId);
            return jdbcTemplate.query(sqlCommand, (rs, rowNum) -> new OrderModel(rs));
        } catch(NullPointerException exception) {
            return new ArrayList<>();
        }
    }
}
