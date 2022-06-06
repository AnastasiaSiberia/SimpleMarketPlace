package com.uwplp.components.DAO;

import com.uwplp.components.models.OrderModel;
import com.uwplp.components.models.ProductModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.parameters.P;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductsDAO extends DAO{
    private static final Logger log = LoggerFactory.getLogger(ProductsDAO.class);

    public ProductsDAO (DataSource dataSource) {
        super(dataSource, "products", "product_sequence");
    }

    public List<ProductModel> readAllProductInfo() {
        return new ArrayList<>(jdbcTemplate.query(
                "SELECT product_id, product_name, product_nviews, product_description, " +
                        "product_rating, product_nreviews, vendor_id, username, product_price FROM " + TABLE_NAME +
                        " left join " + UsersDAO.TABLE_NAME + " on vendor_id = user_id " +
                        "WHERE product_enable=1 AND product_quantity > 0" ,
                (res, rowNum) -> new ProductModel(res)
        ));
    }

    public ProductModel readByID(Long productId) {
        List<ProductModel> response = new ArrayList<>(jdbcTemplate.query(
                "SELECT products.*, vendor_id, username FROM " + TABLE_NAME +
                        " left join " + UsersDAO.TABLE_NAME + " on vendor_id = user_id " + " WHERE product_id = ?",
                new Object[]{productId},
                (res, rowNum) -> new ProductModel(res)
        ));
        log.debug("the response for readByID was created");
        return response.get(0);
    }
    public void addProduct(ProductModel productModel) {
        String command = String.format(
                "INSERT INTO %s(product_id, product_name, product_description, vendor_id, product_price, product_quantity)  values(%d, '%s', '%s', %d, %d, %d)",
                TABLE_NAME,
                productModel.getProduct_id(),
                productModel.getProduct_name(),
                productModel.getProduct_description(),
                productModel.getVendor_id(),
                productModel.getProduct_price(),
                productModel.getProduct_quantity()
        );
        jdbcTemplate.execute(command);
    }

    public void disableProduct(Long productId) {
        String sqlCommand = String.format("UPDATE %s SET product_enable = 0 WHERE product_id = %d",
                TABLE_NAME, productId);
        jdbcTemplate.execute(sqlCommand);
    }

    void changeQuantity(Long productId, Long productQuantity) {
        String command = String.format("UPDATE %s SET product_quantity = %d WHERE product_id = %d",
                TABLE_NAME, productQuantity, productId);
        jdbcTemplate.update(command);
    }

    public void subtract(List<OrderModel> orders) {
        subtractOrders(orders);
        if(!validatePositiveQuantity()) {
            subtractOrders(orders.stream()
                    .peek(order -> order.setOrder_size(-order.getOrder_size()))
                    .collect(Collectors.toList()));
        }
        else {
            throw new IllegalArgumentException("Some products are not enough");
        }
    }

    private boolean validatePositiveQuantity() {
        try {
            String sqlCommand = String.format("SELECT product_id FROM %s WHERE product_quantity < 0", TABLE_NAME);
            jdbcTemplate.query(sqlCommand, (rs, rowNum) -> rs.getLong("product_id"));
            return false;
        } catch (NullPointerException ex) {
            return true;
        }
    }

    private void subtractOrders(List<OrderModel> orders) {
        orders.forEach((order) -> {
            ProductModel pm = readByID(order.getProduct_id());
            order.setOrder_price(pm.getProduct_price());
            order.setOrder_time(new Date());
            changeQuantity(pm.getProduct_id(), pm.getProduct_quantity() - order.getOrder_size());
        });
    }

    public void addViews(Long productId, Long size) {
        String sqlCommand = String.format("UPDATE %s " +
                        "SET product_nviews = product_nviews + %d " +
                        "WHERE product_id = %d",
                TABLE_NAME, size, productId);
        jdbcTemplate.execute(sqlCommand);
    }

    public void addRating(Long productId, Long reviewValue) {
        String sqlCommand = String.format("UPDATE %s " +
                        "SET product_rating = product_rating + %d, " +
                        "product_nreviews = product_nreviews + 1 " +
                        "WHERE product_id = %d",
                TABLE_NAME, reviewValue, productId);
        jdbcTemplate.execute(sqlCommand);
    }
}
