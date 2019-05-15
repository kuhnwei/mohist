package com.szatc.demo.dao;

import com.szatc.demo.domain.Example;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 操作example数据表  DAO接口
 * @author Kuhn Wei, email@kuhnwei.com
 * @date 2018/5/21 20:48
 */
@Mapper
public interface ExampleDao {

    /**
     * 插入一条新数据
     * @param example 数据
     * @return 插入成功返回：1
     */
    int save(Example example);

    /**
     * 根据ID删除一条数据（逻辑删除）
     * @param example ID
     * @return 删除成功返回：1
     */
    int remove(Example example);

    /**
     * 根据ID查询数据
     * @param example ID
     * @return Example
     */
    Example get(Example example);

    /**
     * 列表查询
     * @param example example
     * @return List<Example>
     */
    List<Example> list(Example example);

    /**
     * 统计总记录数
     * @param example example
     * @return 总记录数
     */
    long count(Example example);

    /**
     * 修改信息
     * @param example 数据
     * @return 修改成功返回：1
     */
    int update(Example example);
}
