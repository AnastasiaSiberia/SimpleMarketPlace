package com.uwplp.components;

import org.json.JSONArray;

public interface DAO {
    JSONArray readAll();
    JSONArray readByID(Long id);
    JSONArray updateByID(Long id);
    //public JSONArray updateFew(JSONArray);
}
