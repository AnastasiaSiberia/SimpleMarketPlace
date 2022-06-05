package com.uwplp.components.DAO;

import com.uwplp.components.models.OrderModel;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAO extends DAO{
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public OrdersDAO (DataSource dataSource) {
        super(dataSource, "orders", "order_sequence");
    }

    public void addOrders(List<OrderModel> orders) {
        orders.forEach(order -> {
            String command = String.format(
                    "INSERT INTO %s  values(%d, %d, %d, %d, %d, TO_DATE('%s', 'dd/MM/YYYY'))",
                    TABLE_NAME,
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
    public List<OrderModel> getOrdersByUserID(long userId) {
        try {
            String sqlCommand = String.format("SELECT * from %s WHERE user_id = %d", TABLE_NAME, userId);
            return jdbcTemplate.query(sqlCommand, (rs, rowNum) -> new OrderModel(rs));
        } catch(NullPointerException exception) {
            return new ArrayList<>();
        }
    }
}
