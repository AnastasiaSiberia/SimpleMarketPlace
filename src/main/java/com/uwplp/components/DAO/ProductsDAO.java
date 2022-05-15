package com.uwplp.components.DAO;

import com.uwplp.components.models.ProductModel;
import org.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class ProductsDAO{
    private final JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(ProductsDAO.class);
    private final String tableName = "products";

    public ProductsDAO (DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public JSONArray readAll() {
        List<ProductModel> response = new ArrayList<>(jdbcTemplate.query(
                "SELECT * FROM " + tableName,
                (res, rowNum) -> new ProductModel(res)
        ));
        log.debug("the response for readAll was created");
        return new JSONArray(response);
    }
    public List<ProductModel> readAllProductInfo() {
        return new ArrayList<>(jdbcTemplate.query(
                "SELECT product_id, product_name, product_nviews, product_rating, vendor_id FROM " + tableName,
                (res, rowNum) -> new ProductModel(res)
        ));
    }

    public ProductModel readByID(Long productId) {
        List<ProductModel> response = new ArrayList<>(jdbcTemplate.query(
                "SELECT * FROM " + tableName + " WHERE product_id = ?",
                new Object[]{productId},
                (res, rowNum) -> new ProductModel(res)
        ));
        log.debug("the response for readByID was created");
        return response.get(0);
    }

    /*@Override
    public JSONArray updateByID(Long productId, ProductModel newModel) {
        jdbcTemplate.update("UPDATE ? SET product_name = ?, product_views = ?, product_reviews = ?, " +
                "product_rating = ?, product_imagename = ? WHERE product_id = ?", tableName,
                newModel.getProduct_name(), newModel.getProduct_nviews(), newModel.getProduct_nreviews(),
                newModel.getProduct_rating(), newModel.getProduct_imagename(), newModel.getProduct_id());
        return new JSONArray(ResponseEntity.ok());
    }*/
    public JSONArray deleteAll() {
        jdbcTemplate.update("DELETE FROM " + tableName);
        return new JSONArray("[200, OK]");
    }


}
