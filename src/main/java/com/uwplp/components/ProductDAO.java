package com.uwplp.components;

import org.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDAO implements DAO{
    private final JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(ProductDAO.class);

    public ProductDAO (DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        log.debug("JDBCTemplate was created");
    }

    @Override
    public JSONArray readAll() {
        List<ProductModel> response = jdbcTemplate.query(
                "SELECT id, name, views FROM products",
                (res, rowNum) -> new ProductModel(res)
        ).stream().collect(Collectors.toList());
        log.debug("the response for readAll was created");
        return new JSONArray(response);
    }

    @Override
    public JSONArray readByID(Long id) {
        List<ProductModel> response = jdbcTemplate.query(
                "SELECT id, name, views FROM products WHERE id = ?", new Object[]{id},
                (res, rowNum) -> new ProductModel(res)
        ).stream().collect(Collectors.toList());
        log.debug("the response for readByID was created");
        return new JSONArray(Arrays.asList(response));
    }

    @Override
    public JSONArray updateByID(Long id, String name, Long views) {
        jdbcTemplate.update("UPDATE products SET name = ?, views = ? WHERE id = ?", name, views, id);
        return new JSONArray("[200, OK]");
    }


}
