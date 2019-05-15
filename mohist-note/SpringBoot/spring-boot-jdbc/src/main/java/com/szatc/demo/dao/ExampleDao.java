package com.szatc.demo.dao;

import com.szatc.demo.domain.Example;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * 操作example数据表
 * @author Kuhn Wei, email@kuhnwei.com
 * @date 2018/5/17 14:14
 **/
@Repository
public class ExampleDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 插入一条新数据
     * @param example 数据
     * @return 插入成功返回：1
     */
    public int save(final Example example) {
        String sql = "INSERT INTO example(text, gmt_create, gmt_modified, is_deleted) VALUES(?,?,?,?)";
        return this.jdbcTemplate.update(sql, pss -> {
            // pss 是  org.springframework.jdbc.core.PreparedStatementSetter 对象
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            pss.setString(1, example.getText());
            pss.setTimestamp(2, ts);
            pss.setTimestamp(3, ts);
            pss.setInt(4, 0);
        });
    }

    /**
     * 根据ID删除一条数据 (物理删除)
     * @param id ID
     * @return 删除成功返回：1
     */
    public int delete(final long id) {
        String sql = "DELETE FROM example WHERE id = ? ";
        return this.jdbcTemplate.update(sql, pss -> pss.setLong(1, id));
    }

    /**
     * 根据ID删除一条数据（逻辑删除）
     * @param id ID
     * @return 删除成功返回：1
     */
    public int remove(final long id) {
        String sql = "UPDATE example SET is_deleted = '1' WHERE id = ? ";
        return this.jdbcTemplate.update(sql, pss-> pss.setLong(1, id));
    }

    /**
     * 根据ID查询数据
     * @param id ID
     * @return Example
     */
    public Example get(long id) {
        String sql = "SELECT id, text, gmt_create, gmt_modified, is_deleted FROM example WHERE id = ? AND is_deleted = '0'";
        Object[] params = {id};
        List<Example> list = null;
        list = this.jdbcTemplate.query(sql, params, this::mapRow);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 列表查询
     * @return List<Example>
     */
    public List<Example> list() {
        String sql = "SELECT id, text, gmt_create, gmt_modified, is_deleted FROM example WHERE is_deleted = '0'";
        return this.jdbcTemplate.query(sql, this::mapRow);
    }

    /**
     * 统计总记录数
     * @return 总记录数
     */
    public long count() {
        String sql = "SELECT COUNT(*) FROM example WHERE is_deleted = '0'";
        return this.jdbcTemplate.queryForObject(sql, Long.class);
    }

    /**
     * 修改信息
     * @param example 数据
     * @return 修改成功返回：1
     */
    public int update(final Example example) {
        String sql = "UPDATE example SET text = ?, gmt_modified = ? WHERE id = ?";
        return this.jdbcTemplate.update(sql, pss -> {
            // pss 是  org.springframework.jdbc.core.PreparedStatementSetter 对象
            pss.setString(1, example.getText());
            pss.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            pss.setLong(3, example.getId());
        });
    }

    /**
     * Example对象与 example表字段 的映射处理, 用于lambda表达式
     * @param rs ResultSet
     * @param i 循环下标
     * @return Example
     */
    private Example mapRow(ResultSet rs, int i) {
        Example example = new Example();
        try {
            example.setId(rs.getLong("id"));
            example.setText(rs.getString("text"));
            example.setGmtCreate(rs.getDate("gmt_create"));
            example.setGmtModified(rs.getDate("gmt_modified"));
            example.setDeleted(rs.getBoolean("is_deleted"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return example;
    }
}
