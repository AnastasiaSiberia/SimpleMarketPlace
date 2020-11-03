package com.uwplp.components;

import org.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDAO implements DAO{
    private final JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(ProductDAO.class);

    public ProductDAO (DataSource dataSource) {
        log.info("JDBCTEMPLATE CREATED SUCCESSFULLY");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public JSONArray readAll() {
        log.info("Querying for customer records:");
        List<ProductModel> response = jdbcTemplate.query(
                "SELECT id, name, views FROM products",
                (rs, rowNum) -> new ProductModel(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getLong("views")
                )
        ).stream().collect(Collectors.toList());
        return new JSONArray(response);
    }

    @Override
    public JSONArray readByID(Long id) {
        return null;
    }

    @Override
    public JSONArray updateByID(Long id) {
        return null;
    }
}
