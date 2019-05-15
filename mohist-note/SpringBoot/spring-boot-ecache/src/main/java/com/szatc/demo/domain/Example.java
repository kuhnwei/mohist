package com.szatc.demo.domain;

import java.io.Serializable;

/**
 * @author Kuhn Wei, email@kuhnwei.com
 * @date 2018/6/11 9:11
 **/
public class Example implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String comments;

    public Example() {
    }

    public Example(Long id) {
        this.id = id;
    }

    public Example(Long id, String comments) {
        this.id = id;
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Example{" +
                "id=" + id +
                ", comments='" + comments + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
