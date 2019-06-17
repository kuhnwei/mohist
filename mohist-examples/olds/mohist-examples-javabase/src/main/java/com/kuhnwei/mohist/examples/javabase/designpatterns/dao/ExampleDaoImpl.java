package com.kuhnwei.mohist.examples.javabase.designpatterns.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Example实体的DAO实现类
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 16:18
 */
public class ExampleDaoImpl implements ExampleDao {

    /** 数据库连接对象 */
    private Connection conn;

    /** 预编译对象 */
    private PreparedStatement pstmt;

    /**
     * 构造方法， 初始化数据库连接对象
     * @param connection 数据库连接对象
     */
    public ExampleDaoImpl(Connection connection) {
        this.conn = connection;
    }

    @Override
    public int save(ExampleDO entity) throws SQLException {
        String sql = "INSERT INTO example(example) VALUES(?)";
        this.pstmt = this.conn.prepareStatement(sql);
        this.pstmt.setString(1, entity.getExample());
        return this.pstmt.executeUpdate();
    }

    @Override
    public int remove(Serializable id) throws SQLException {
        String sql = "DELETE FROM example WHERE id=?";
        this.pstmt = this.conn.prepareStatement(sql);
        this.pstmt.setInt(1, (int) id);
        return this.pstmt.executeUpdate();
    }

    @Override
    public ExampleDO get(Serializable id) throws SQLException {
        ExampleDO example = null;
        String sql = "SELECT 'id', 'example' FROM example WHERE id=?";
        this.pstmt = this.conn.prepareStatement(sql);
        this.pstmt.setInt(1, (int) id);
        ResultSet rs = this.pstmt.executeQuery();
        if (rs.next()) {
            example = new ExampleDO();
            example.setId(rs.getInt(1));
            example.setExample(rs.getString(2));
        }
        return example;
    }

    @Override
    public List<ExampleDO> list() throws SQLException {
        List<ExampleDO> list = new ArrayList<ExampleDO>();
        String sql = "SELECT id, example FROM example";
        this.pstmt = this.conn.prepareStatement(sql);
        ResultSet rs = this.pstmt.executeQuery();
        while (rs.next()) {
            ExampleDO example = new ExampleDO();
            example.setId(rs.getInt(1));
            example.setExample(rs.getString(2));
            list.add(example);
        }
        return list;
    }

    @Override
    public List<ExampleDO> list(int currentPage, int lineSize) throws SQLException {
        List<ExampleDO> list = new ArrayList<ExampleDO>();
        String sql = "SELECT id, example FROM example LIMIT ?, ?";
        this.pstmt = this.conn.prepareStatement(sql);
        this.pstmt.setInt(1, (currentPage - 1) * lineSize);
        this.pstmt.setInt(2, lineSize);
        ResultSet rs = this.pstmt.executeQuery();
        while (rs.next()) {
            ExampleDO example = new ExampleDO();
            example.setId(rs.getInt(1));
            example.setExample(rs.getString(2));
            list.add(example);
        }
        return list;
    }

    @Override
    public long count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM example";
        this.pstmt = this.conn.prepareStatement(sql);
        ResultSet rs = this.pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    @Override
    public int update(ExampleDO entity) throws SQLException {
        String sql = "UPDATE example SET example=? WHERE id=?";
        this.pstmt = this.conn.prepareStatement(sql);
        this.pstmt.setString(1, entity.getExample());
        this.pstmt.setInt(2, entity.getId());
        return this.pstmt.executeUpdate();
    }

}
