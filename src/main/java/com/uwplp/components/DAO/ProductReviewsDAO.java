package com.uwplp.components.DAO;

import com.uwplp.components.models.ProductReviewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductReviewsDAO {
    private final JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(ProductsDAO.class);
    public static final String TABLENAME = "product_reviews";

    public ProductReviewsDAO (DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<ProductReviewModel> getReviews(Long id) {
        String sqlCommand = String.format("SELECT %s.*, username FROM %s " +
                "left join %s on %s.user_id = %s.user_id " +
                "WHERE product_id = %d ", TABLENAME, TABLENAME, UsersDAO.TABLENAME,
                TABLENAME, UsersDAO.TABLENAME, id);
        try {
            return jdbcTemplate.query(sqlCommand, (rs, rowNum) -> new ProductReviewModel(rs));
        } catch (NullPointerException ex) {
            return new ArrayList<>();
        }
    }

    public void addProductReview(ProductReviewModel model) {
        model.setProduct_review_id(getNextProductReviewId());
        String sqlCommand = String.format("INSERT INTO %s VALUES(%d, %d, %d, '%s', %d)",
                TABLENAME,
                model.getProduct_review_id(),
                model.getProduct_id(),
                model.getReview_value(),
                model.getReview_text(),
                model.getUser_id());
        jdbcTemplate.update(sqlCommand);
    }

    private Long getNextProductReviewId() {
        try {
            List<Map<String, Object>> maxIds = jdbcTemplate.queryForList("SELECT max(product_review_id) from " + TABLENAME);
            return Long.parseLong(maxIds.get(0).get("max").toString()) + 1;
        } catch(NullPointerException exception) {
            return 0L;
        }
    }
}
