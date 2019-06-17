package mohist.examples.spring.springboot.dao;

import lombok.extern.slf4j.Slf4j;
import mohist.examples.spring.springboot.entity.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/13
 **/
@Slf4j
@Repository
public class ExampleDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ExampleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Example example) {
        long currentTimeMillis = System.currentTimeMillis();
        example.setId(UUID.randomUUID().toString().replace("-", ""));
        example.setGmtCreate(currentTimeMillis);
        example.setGmtModified(currentTimeMillis);
        example.setDeleted(false);
        String sql = "INSERT INTO example (id, content, gmt_create, gmt_modified, deleted) VALUES (?, ?, ?, ?, ?)";
        try {
            int result = this.jdbcTemplate.update(sql, pss -> {
                pss.setString(1, example.getId());
                pss.setString(2, example.getContent());
                pss.setLong(3, example.getGmtCreate());
                pss.setLong(4, example.getGmtModified());
                pss.setInt(5, 0);
            });
            log.debug("[ save ] result: {}, sql: {}, by id: {}", result, sql, example.getId());
        } catch (DataAccessException e) {
            log.error("[ save ] Error exception message: {}", e.getMessage());
        }
    }

    public void delete(final String id) {
        String sql = "DELETE FROM example WHERE id = ?";
        try {
            int result = this.jdbcTemplate.update(sql, pss -> pss.setString(1, id));
            log.debug("[ delete ] result: {}, sql: {}, by id: {}", result, sql, id);
        } catch (DataAccessException e) {
            log.error("[ delete ] Error exception message: {}", e.getMessage());
        }
    }

    public void remove(final String id) {
        String sql = "UPDATE example SET deleted = '1' WHERE id = ?";
        try {
            int result = this.jdbcTemplate.update(sql, pss -> pss.setString(1, id));
            log.debug("[ remove ] result: {}, sql: {}, by id: {}", result, sql, id);
        } catch (DataAccessException e) {
            log.error("[ remove ] Error exception message: {}", e.getMessage());
        }
    }

    public Example get(final String id) {
        String sql = "SELECT id, content, gmt_modified, gmt_create, deleted FROM example WHERE id = ?";
        Object[] params = {id};
        List<Example> list = null;
        list = this.jdbcTemplate.query(sql, params, this::mapRow);
        boolean isEmpty = CollectionUtils.isEmpty(list);
        log.debug("[ get ] result: {}, id: {}", isEmpty ? "null" : list.get(0).toString(), id);
        if (isEmpty) {
            return null;
        }
        return list.get(0);
    }

    /*
    TODO queryForObject
    public Example get(final String id) {
        String sql = "SELECT id, content, gmt_modified, gmt_create, deleted FROM example WHERE id = ?";
        Object[] params = {id};
        try {
            Example example = this.jdbcTemplate.queryForObject(sql, this::mapRow, params);
            log.debug("[ get ] result: {}, by id: {}", example, id);
            return example;
        } catch (EmptyResultDataAccessException e) {
            log.error("[ get ] result: {}, by id: {}", "null", id);
            return null;
        }
    }
    */

    public List<Example> listAll() {
        String sql = "SELECT id, content, gmt_modified, gmt_create, deleted FROM example";
        List<Example> list = this.jdbcTemplate.query(sql, this::mapRow);
        log.debug("[ listAll ] result the size: {}", list.size());
        return list;
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM example";
        return this.jdbcTemplate.queryForObject(sql, Long.class);
    }

    public void update(Example example) {
        String sql = "UPDATE example SET content = ?, gmt_modified = ? WHERE id = ?";
        example.setGmtModified(System.currentTimeMillis());
        int result = this.jdbcTemplate.update(sql, pss -> {
            pss.setString(1, example.getContent());
            pss.setLong(2, example.getGmtModified());
            pss.setString(3, example.getId());
        });
        log.debug("[ update ] result: {}, by id: {}", result, example.getId());
    }

    private Example mapRow(ResultSet rs, int i) {
        Example example = new Example();
        try {
            example.setId(rs.getString("id"));
            example.setContent(rs.getString("content"));
            example.setGmtModified(rs.getLong("gmt_modified"));
            example.setGmtCreate(rs.getLong("gmt_create"));
            example.setDeleted(rs.getInt("deleted") == 1);
        } catch (SQLException e) {
            log.error("[ mapRow ] Error exception message: {}", e.getMessage());
        }
        return example;
    }
}
