package com.uwplp.components.models;

import com.uwplp.utils.ResultSetColumnChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.sql.ResultSet;
import java.sql.SQLException;

import static java.util.Objects.hash;

@Entity
public class ProductModel{
    private static final Logger log = LoggerFactory.getLogger(ProductModel.class);

    private @Id Long product_id;
    private String product_name;
    private String product_description;
    private Long product_nviews;
    private Long product_nreviews;
    private Double product_rating;
    private String vendor_name;

    private Long product_price;

    private Long product_quantity;

    private Long vendor_id;

    public ProductModel() {}

    public ProductModel(ResultSet rs) throws SQLException {
        ResultSetColumnChecker checker = new ResultSetColumnChecker(rs);
        this.product_id = rs.getLong("product_id");
        if(checker.hasColumn("product_name"))
            this.product_name = rs.getString("product_name").trim();
        if(checker.hasColumn("product_description"))
            this.product_description = rs.getString("product_description");
        if(checker.hasColumn("product_nviews"))
            this.product_nviews = rs.getLong("product_nviews");
        if(checker.hasColumn("product_nreviews"))
            this.product_nreviews = rs.getLong("product_nreviews");
        if(checker.hasColumn("product_rating"))
            this.product_rating = rs.getDouble("product_rating");
        if(checker.hasColumn("username"))
            this.vendor_name = rs.getString("username");
        if(checker.hasColumn("vendor_id"))
            this.vendor_id = rs.getLong("vendor_id");
        if(checker.hasColumn("product_price"))
            this.product_price = rs.getLong("product_price");
        if(checker.hasColumn("product_quantity"))
            this.product_quantity = rs.getLong("product_quantity");
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public Long getProduct_nviews() {
        return product_nviews;
    }

    public void setProduct_nviews(Long product_nviews) {
        this.product_nviews = product_nviews;
    }

    public Long getProduct_nreviews() {
        return product_nreviews;
    }

    public void setProduct_nreviews(Long product_nreviews) {
        this.product_nreviews = product_nreviews;
    }

    public Double getProduct_rating() {
        return product_rating;
    }

    public void setProduct_rating(Double product_rating) {
        this.product_rating = product_rating;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendorName) {
        this.vendor_name = vendorName;
    }

    public Long getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(Long vendor_id) {
        this.vendor_id = vendor_id;
    }

    public Long getProduct_price() {
        return product_price;
    }

    public void setProduct_price(Long product_price) {
        this.product_price = product_price;
    }

    public Long getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(Long product_quantity) {
        this.product_quantity = product_quantity;
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "product_id=" + product_id +
                ", product_name='" + product_name + '\'' +
                ", product_description='" + product_description + '\'' +
                ", product_nviews=" + product_nviews +
                ", product_nreviews=" + product_nreviews +
                ", product_rating=" + product_rating +
                ", vendorName='" + vendor_name + '\'' +
                ", vendor_id=" + vendor_id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductModel that = (ProductModel) o;
        return product_id.equals(that.product_id)
                && product_name.equals(that.product_name)
                && product_description.equals(that.product_description)
                && product_nviews.equals(that.product_nviews)
                && product_nreviews.equals(that.product_nreviews)
                && product_rating.equals(that.product_rating)
                && vendor_name.equals(that.vendor_name)
                && vendor_id.equals(that.vendor_id);
    }

    @Override
    public int hashCode() {
        return hash(product_id, product_name, product_description, product_nviews, product_nreviews, product_rating, vendor_name, vendor_id);
    }

}
