package com.uwplp.components;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.swing.tree.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static java.util.Objects.hash;

@Entity
public class ProductModel{
    private @Id @GeneratedValue Long id;

    private String name;
    private Long views;

    public ProductModel() {}

    public ProductModel(String name, Long views) {
        this.name = name;
        this.views = views;
    }

    public ProductModel(Long id, String name, Long views) {
        this.id = id;
        this.name = name;
        this.views = views;
    }

    public ProductModel(ResultSet rs) throws SQLException {
        this.id = rs.getLong("id");
        this.name = rs.getString("name");
        this.views = rs.getLong("views");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", views=" + views +
                '}';
    }

    @Override
    public boolean equals(Object Ob) {
        if(!(Ob instanceof ProductModel)) return false;
        ProductModel other = (ProductModel)Ob;
        return this.id == other.id && this.name.equals(other.name) && this.views == other.views;
    }

    @Override
    public int hashCode() {
        return hash(this.id, this.name, this.views);
    }
}
