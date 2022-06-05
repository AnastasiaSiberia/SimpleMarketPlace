package com.uwplp.components.DAO;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public abstract class DAO {
    protected JdbcTemplate jdbcTemplate;
    protected final String TABLE_NAME;
    protected final String SEQUENCE_NAME;

    public DAO (DataSource dataSource, String TABLE_NAME, String SEQUENCE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
        this.SEQUENCE_NAME = SEQUENCE_NAME;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long getNextId() {
        String sqlCommand = String.format("SELECT nextval('%s')", SEQUENCE_NAME);
        return jdbcTemplate.query(sqlCommand, (rs, rowNum) -> rs.getLong("nextval")).get(0);
    }

}
