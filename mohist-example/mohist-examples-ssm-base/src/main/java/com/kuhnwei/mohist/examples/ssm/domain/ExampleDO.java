package com.kuhnwei.mohist.examples.ssm.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/3/14 9:41
 */
public class ExampleDO implements Serializable {

    private Long id;
    private String exampleStr;
    private Date gmtCreate;
    private Date gmtModified;
    private Boolean deleted;

    public ExampleDO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExampleStr() {
        return exampleStr;
    }

    public void setExampleStr(String exampleStr) {
        this.exampleStr = exampleStr;
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

    @Override
    public String toString() {
        return "ExampleDO{" +
                "id=" + id +
                ", exampleStr='" + exampleStr + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", deleted=" + deleted +
                '}';
    }
}
