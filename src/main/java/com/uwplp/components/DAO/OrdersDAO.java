package com.uwplp.components.DAO;

import com.uwplp.components.models.OrderModel;
import com.uwplp.components.models.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class OrdersDAO {

    private final JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(OrdersDAO.class);
    public static final String TABLENAME = "orders";

    public OrdersDAO (DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addOrders(List<OrderModel> orders) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");
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
        try {
            List<Map<String, Object>> maxIds = jdbcTemplate.queryForList("SELECT max(order_id) from " + TABLENAME);
            return Long.parseLong(maxIds.get(0).get("max").toString()) + 1;
        } catch(NullPointerException exception) {
            return 0L;
        }
    }
}
