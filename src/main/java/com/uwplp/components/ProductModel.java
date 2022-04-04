package com.uwplp.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

import static java.util.Objects.hash;

@Entity
public class ProductModel{
    private static final Logger log = LoggerFactory.getLogger(ProductModel.class);

    private @Id Long product_id;
    private String product_name;
    private Long product_views;
    private Long product_reviews;
    private Double product_rating;
    private String product_imagename;
    private Long vendor_id;

    public ProductModel() {}

    public ProductModel(String product_name, Long product_views) {
        this.product_name = product_name;
        this.product_views = product_views;
    }

    public ProductModel(Long product_id, String product_name, Long product_views) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_views = product_views;
    }

    public ProductModel(String string) {
        String[] splitted = string.split(",");
        log.debug(Arrays.toString(splitted));
        this.product_id = Long.valueOf(splitted[0]);
        this.product_name = splitted[1];
        this.product_views = 0L;
    }

    public ProductModel(ResultSet rs) throws SQLException {
        this.product_id = rs.getLong("product_id");
        this.product_name = rs.getString("product_name");
        this.product_views = rs.getLong("product_views");
        this.product_reviews = rs.getLong("product_reviews");
        this.product_rating = rs.getDouble("product_rating");
        this.product_imagename = rs.getString("product_imagename");
        this.vendor_id = rs.getLong("vendor_id");
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long id) {
        this.product_id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String name) {
        this.product_name = name;
    }

    public Long getProduct_views() {
        return product_views;
    }

    public void setProduct_views(Long views) {
        this.product_views = views;
    }
    public void addView() {
        this.product_views++;
    }

    public Long getProduct_reviews() {
        return product_reviews;
    }

    public void setProduct_reviews(Long product_reviews) {
        this.product_reviews = product_reviews;
    }

    public Double getProduct_rating() {
        return product_rating;
    }

    public void setProduct_rating(Double product_rating) {
        this.product_rating = product_rating;
    }

    public String getProduct_imagename() {
        return product_imagename;
    }

    public void setProduct_imagename(String product_imagename) {
        this.product_imagename = product_imagename;
    }

    public Long getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(Long vendor_id) {
        this.vendor_id = vendor_id;
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "product_id=" + product_id +
                ", product_name='" + product_name + '\'' +
                ", product_views=" + product_views +
                ", product_reviews=" + product_reviews +
                ", product_rating=" + product_rating +
                ", product_imagename='" + product_imagename + '\'' +
                ", vendor_id=" + vendor_id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductModel that = (ProductModel) o;
        return product_id.equals(that.product_id) && product_name.equals(that.product_name) && Objects.equals(product_views, that.product_views) && Objects.equals(product_reviews, that.product_reviews) && Objects.equals(product_rating, that.product_rating) && Objects.equals(product_imagename, that.product_imagename) && vendor_id.equals(that.vendor_id);
    }

    @Override
    public int hashCode() {
        return hash(product_id, product_name, product_views, product_reviews, product_rating, product_imagename, vendor_id);
    }
}
