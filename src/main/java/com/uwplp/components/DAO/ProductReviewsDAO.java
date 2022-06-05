package com.uwplp.components.DAO;

import com.uwplp.components.models.ProductReviewModel;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductReviewsDAO extends DAO{
    public ProductReviewsDAO (DataSource dataSource) {
        super(dataSource, "product_reviews", "product_reviews_sequence");
    }

    public List<ProductReviewModel> getReviews(Long id) {
        String sqlCommand = String.format("SELECT %s.*, username FROM %s " +
                "left join %s on %s.user_id = %s.user_id " +
                "WHERE product_id = %d ",
                TABLE_NAME,
                TABLE_NAME,
                UsersDAO.TABLENAME,
                TABLE_NAME,
                UsersDAO.TABLENAME,
                id);
        try {
            return jdbcTemplate.query(sqlCommand, (rs, rowNum) -> new ProductReviewModel(rs));
        } catch (NullPointerException ex) {
            return new ArrayList<>();
        }
    }

    public void addProductReview(ProductReviewModel model) {
        if(model.getReview_value() < 0 || model.getReview_value() > 5) {
            throw new IllegalArgumentException("A review value must be between 0 and 5");
        }
        model.setProduct_review_id(getNextId());
        String sqlCommand = String.format("INSERT INTO %s VALUES(%d, %d, %d, '%s', %d)",
                TABLE_NAME,
                model.getProduct_review_id(),
                model.getProduct_id(),
                model.getReview_value(),
                model.getReview_text(),
                model.getUser_id());
        jdbcTemplate.update(sqlCommand);
    }
}
