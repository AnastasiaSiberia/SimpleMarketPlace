package com.uwplp.components;

import org.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ProductDAO implements DAO{
    private final JdbcTemplate jdbcTemplate;
    private final ExecutorService executorService;
    private static final Logger log = LoggerFactory.getLogger(ProductDAO.class);

    public ProductDAO (DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        log.debug("JDBCTemplate was created");
        executorService = new ThreadPoolExecutor(
                2, 4, 5L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    @Override
    public JSONArray readAll() {
        List<ProductModel> response = new ArrayList<>(jdbcTemplate.query(
                "SELECT * FROM product",
                (res, rowNum) -> new ProductModel(res)
        ));
        log.debug("the response for readAll was created");
        return new JSONArray(response);
    }

    @Override
    public JSONArray readByID(Long productId) {
        List<ProductModel> response = new ArrayList<>(jdbcTemplate.query(
                "SELECT * FROM product WHERE product_id = ?", new Object[]{productId},
                (res, rowNum) -> new ProductModel(res)
        ));
        if(response.isEmpty()) {
            log.warn("readByID didn't find a product with product_id = {}", productId);
        }
        log.debug("the response for readByID was created");
        return new JSONArray(response);
    }

    @Override
    public JSONArray updateByID(Long productId, ProductModel newModel) {
        jdbcTemplate.update("UPDATE product SET product_name = ?, product_views = ?, product_reviews = ?, " +
                "product_rating = ?, product_imagename = ? WHERE product_id = ?",
                newModel.getProduct_name(), newModel.getProduct_views(), newModel.getProduct_reviews(),
                newModel.getProduct_rating(), newModel.getProduct_imagename(), newModel.getProduct_id());
        return new JSONArray(ResponseEntity.ok());
    }

    @Transactional
    protected JSONArray updateTransaction(List <ProductModel> for_update, List <ProductModel> for_adding) {
        List<Object[]> addList = for_adding.stream().map(
                (x) -> new Object[] {x.getProduct_id(), x.getProduct_name()}
        ).collect(Collectors.toList());
        jdbcTemplate.batchUpdate("INSERT INTO product(product_id, product_name, product_views) VALUES(?, ?, 0)", addList);

        List<Object[]> updateList = for_update.stream().map(
                (x) -> new Object[] {x.getProduct_name(), x.getProduct_imagename(), x.getProduct_id()}
        ).collect(Collectors.toList());
        jdbcTemplate.batchUpdate("UPDATE product SET product_name = ?, product_imagename = ? WHERE product_id = ?", updateList);
        return new JSONArray(ResponseEntity.ok());
    }

    public JSONArray updateFew(String csvFile) {
        executorService.execute(() -> {
            log.debug("new Runnable was added into queue");
            List <ProductModel> products = Arrays.stream(csvFile.split("\n")).skip(1).sorted().
                    map(ProductModel::new).collect(Collectors.toList());
            log.debug("product was splitted");
            List <ProductModel> for_update = products.stream().filter(
                    (x) -> (jdbcTemplate.query(
                            "SELECT * FROM product WHERE product_id = ?", new Object[]{x.getProduct_id()},
                            (res, rowNum) -> new ProductModel(res)
                    ).size() == 1)
            ).collect(Collectors.toList());
            log.debug("queue for updating : " + for_update);

            List <ProductModel> for_adding = products.stream().filter(
                    (x) -> (jdbcTemplate.query(
                            "SELECT * FROM product WHERE product_id = ?", new Object[]{x.getProduct_id()},
                            (res, rowNum) -> new ProductModel(res)
                    ).size() == 0)
            ).collect(Collectors.toList());
            log.debug("queue for adding : " + for_adding);
            updateTransaction(for_update, for_adding);
        });
        return new JSONArray("[200, OK]");
    }

    public JSONArray getStatistic(Long maxSize) {
        List<ProductModel> statistic = jdbcTemplate.query(
                "SELECT * FROM product ORDER BY product_views DESC LIMIT ?", new Object[] {maxSize},
                (rs, rowNum) -> new ProductModel(rs)
                );
        return new JSONArray(statistic);
    }

    public JSONArray deleteAll() {
        jdbcTemplate.update("DELETE FROM product");
        return new JSONArray("[200, OK]");
    }
}
