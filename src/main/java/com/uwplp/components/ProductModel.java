package com.uwplp.components;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//@Entity
public class ProductModel {
    private /*@Id @GeneratedValue*/ Long id;

    private String name;
    private Long views;

    public ProductModel(Long id, String name, Long views) {
        this.id = id;
        this.name = name;
        this.views = views;
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
}
