package com.kuhnwei.mohist.examples.javabase.designpatterns.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * DAO基础接口
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 16:05
 */
public interface BaseDao<T> {

    /**
     * 保存
     * @param entity 实体对象
     * @return int
     */
    int save(T entity) throws SQLException;

    /**
     * 根据ID删除
     * @param id 被删除的实体ID
     * @return int
     */
    int remove(Serializable id) throws SQLException;

    /**
     * 根据ID获取对象
     * @param id 被查询的实体ID
     * @return T
     */
    T get(Serializable id) throws SQLException;

    /**
     * 获取数据表所有数据
     * @return List
     */
    List<T> list() throws SQLException;

    /**
     * 分页获取数据表数据
     * @param currentPage 当前页
     * @param lineSize 每页的数据行数
     * @return List
     */
    List<T> list(int currentPage, int lineSize) throws SQLException;

    /**
     * 统计数据表的数据总数
     * @return long 总数
     */
    long count() throws SQLException;

    /**
     * 更新
     * @param entity 将要更新的内容
     * @return int
     */
    int update(T entity) throws SQLException;

}
