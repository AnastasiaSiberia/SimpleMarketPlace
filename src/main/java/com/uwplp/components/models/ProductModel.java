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
    private String product_imagename;
    private Long vendor_id;

    public ProductModel() {}

    public ProductModel(ResultSet rs) throws SQLException {
        ResultSetColumnChecker checker = new ResultSetColumnChecker(rs);
        this.product_id = rs.getLong("product_id");
        if(checker.hasColumn("product_name"))
            this.product_name = rs.getString("product_name");
        if(checker.hasColumn("product_description"))
            this.product_description = rs.getString("product_description");
        if(checker.hasColumn("product_nviews"))
            this.product_nviews = rs.getLong("product_nviews");
        if(checker.hasColumn("product_nreviews"))
            this.product_nreviews = rs.getLong("product_nreviews");
        if(checker.hasColumn("product_rating"))
            this.product_rating = rs.getDouble("product_rating");
        //this.product_imagename = rs.getString("product_imagename");
        if(checker.hasColumn("vendor_id"))
            this.vendor_id = rs.getLong("vendor_id");
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductModel that = (ProductModel) o;
        return product_id.equals(that.product_id) && product_name.equals(that.product_name) && product_description.equals(that.product_description) && product_nviews.equals(that.product_nviews) && product_nreviews.equals(that.product_nreviews) && product_rating.equals(that.product_rating) && vendor_id.equals(that.vendor_id);
    }

    @Override
    public int hashCode() {
        return hash(product_id, product_name, product_description, product_nviews, product_nreviews, product_rating, vendor_id);
    }
}
