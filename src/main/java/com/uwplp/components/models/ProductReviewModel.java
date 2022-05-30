package com.uwplp.components.models;

import com.uwplp.utils.ResultSetColumnChecker;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductReviewModel {
    private Long product_review_id;
    private Long product_id;
    private Long review_value;
    private String review_text;

    private Long user_id;

    private String username;

    public ProductReviewModel() {}
    public ProductReviewModel(ResultSet rs) throws SQLException {
        ResultSetColumnChecker checker = new ResultSetColumnChecker(rs);
        if(checker.hasColumn("product_review_id"))
            this.product_review_id = rs.getLong("product_review_id");
        if(checker.hasColumn("product_id"))
            this.product_id = rs.getLong("product_id");
        if(checker.hasColumn("review_value"))
            this.review_value = rs.getLong("review_value");
        if(checker.hasColumn("review_text"))
            this.review_text = rs.getString("review_text");
        if(checker.hasColumn("user_id"))
            this.user_id = rs.getLong("user_id");
        if(checker.hasColumn("username"))
            this.username = rs.getString("username");
    }

    public Long getProduct_review_id() {
        return product_review_id;
    }

    public void setProduct_review_id(Long product_review_id) {
        this.product_review_id = product_review_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Long getReview_value() {
        return review_value;
    }

    public void setReview_value(Long review_value) {
        this.review_value = review_value;
    }

    public String getReview_text() {
        return review_text;
    }

    public void setReview_text(String review_text) {
        this.review_text = review_text;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
