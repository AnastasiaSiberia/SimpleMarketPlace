package com.uwplp.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Objects;

public class ResultSetColumnChecker{
    private final ResultSetMetaData metaData;
    public ResultSetColumnChecker(ResultSet rs) throws SQLException {
        metaData = rs.getMetaData();
    }

    public boolean hasColumn(String column_name) throws SQLException {
        for(int i = 1; i <= metaData.getColumnCount(); i++) {
            if(Objects.equals(metaData.getColumnName(i), column_name)) return true;
        }
        return false;
    }

}
