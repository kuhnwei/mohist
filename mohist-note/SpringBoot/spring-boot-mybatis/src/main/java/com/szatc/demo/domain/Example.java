package com.szatc.demo.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 实体类
 * @author Kuhn Wei, email@kuhnwei.com
 * @date 2018/5/17 14:09
 **/
public class Example implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final boolean DELETED_TRUE = true;
    private static final boolean DELETED_FALSE = false;

    /**
     * 主键 ID
     */
    private Long id;

    /**
     *
     */
    private String text;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 表示逻辑删除，1 ：已删除；0：未删除
     */
    private Boolean deleted;

    public Example() {
    }

    public Example(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Example{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", deleted=" + deleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Example example = (Example) o;
        return Objects.equals(id, example.id) &&
                Objects.equals(text, example.text) &&
                Objects.equals(gmtCreate, example.gmtCreate) &&
                Objects.equals(gmtModified, example.gmtModified) &&
                Objects.equals(deleted, example.deleted);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, text, gmtCreate, gmtModified, deleted);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
