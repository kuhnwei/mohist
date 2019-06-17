package com.kuhnwei.mohist.examples.javabase.designpatterns.dao;

import java.io.Serializable;

/**
 * 实体对象
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 16:14
 */
public class ExampleDO implements Serializable {

    private Integer id;
    private String example;

    public ExampleDO() {
    }

    @Override
    public String toString() {
        return "ExampleDO{" +
                "id=" + id +
                ", example='" + example + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        ExampleDO exampleDO = (ExampleDO) object;

        if (id != null ? !id.equals(exampleDO.id) : exampleDO.id != null) return false;
        return example != null ? example.equals(exampleDO.example) : exampleDO.example == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (example != null ? example.hashCode() : 0);
        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
