package com.uwplp.components;

import org.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
                "SELECT id, name, views FROM products",
                (res, rowNum) -> new ProductModel(res)
        ));
        log.debug("the response for readAll was created");
        return new JSONArray(response);
    }

    @Override
    public JSONArray readByID(Long id) {
        List<ProductModel> response = new ArrayList<>(jdbcTemplate.query(
                "SELECT id, name, views FROM products WHERE id = ?", new Object[]{id},
                (res, rowNum) -> new ProductModel(res)
        ));
        log.debug("the response for readByID was created");
        if(response.size() > 0) {
            response.get(0).addView();
            updateByID(id, response.get(0).getName(), response.get(0).getViews());
        }
        else {
            log.warn("readByID didn't find a product with id = {}", id);
        }
        return new JSONArray(response);
    }

    @Override
    public JSONArray updateByID(Long id, String name, Long views) {
        jdbcTemplate.update("UPDATE products SET name = ?, views = ? WHERE id = ?", name, views, id);
        return new JSONArray("[200, OK]");
    }

    @Transactional
    protected void updateTransaction(List <ProductModel> for_update, List <ProductModel> for_adding) {
        List<Object[]> temp = for_adding.stream().map(
                (x) -> new Object[] {x.getId(), x.getName()}
        ).collect(Collectors.toList());
        jdbcTemplate.batchUpdate("INSERT INTO products(id, name, views) VALUES(?, ?, 0)", temp);

        temp = for_update.stream().map(
                (x) -> new Object[] {x.getName(), x.getId()}
        ).collect(Collectors.toList());
        jdbcTemplate.batchUpdate("UPDATE products SET name = ? WHERE id = ?", temp);

    }

    public JSONArray updateFew(String csvFile) {
        executorService.execute(() -> {
            log.debug("new Runnable was added into queue");
            List <ProductModel> products = Arrays.stream(csvFile.split("\n")).skip(1).sorted().
                    map(ProductModel::new).collect(Collectors.toList());
            log.debug("product was splitted");
            List <ProductModel> for_update = products.stream().filter(
                    (x) -> (jdbcTemplate.query(
                            "SELECT id, name, views FROM products WHERE id = ?", new Object[]{x.getId()},
                            (res, rowNum) -> new ProductModel(res)
                    ).size() == 1)
            ).collect(Collectors.toList());
            log.debug("queue for updating : " + for_update);

            List <ProductModel> for_adding = products.stream().filter(
                    (x) -> (jdbcTemplate.query(
                            "SELECT id, name, views FROM products WHERE id = ?", new Object[]{x.getId()},
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
                "SELECT id, name, views FROM products ORDER BY views DESC LIMIT ?", new Object[] {maxSize},
                (rs, rowNum) -> new ProductModel(rs)
                );
        return new JSONArray(statistic);
    }

    public JSONArray deleteAll() {
        jdbcTemplate.update("DELETE FROM products");
        return new JSONArray("[200, OK]");
    }
}
