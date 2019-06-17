package mohist.examples.spring.springboot.dao;

import mohist.examples.spring.springboot.entity.Example;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/17
 **/
@Mapper
public interface ExampleDao {

    @Insert(value = "INSERT INTO example(id, content, gmt_create, gmt_modified, deleted) VALUES(#{id}, #{content}, #{gmtCreate}, #{gmtModified}, #{deleted, jdbcType=TINYINT})")
    void save(Example example);

    @Update(value = "UPDATE example deleted = #{deleted, jdbcType=TINYINT} WHERE id = #{id}")
    void remove(String id);

    @Delete(value = "DELETED FROM example WHERE id = #{id}")
    void delete(String id);

    @Select(value = "SELECT id, content, gmt_create, gmt_modified, deleted FROM example")
    @Results(id = "mapRow", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "gmt_create", property = "gmtCreate", jdbcType = JdbcType.BIGINT),
            @Result(column = "gmt_modified", property = "gmtModified", jdbcType = JdbcType.BIGINT),
            @Result(column = "deleted", property = "deleted", jdbcType = JdbcType.TINYINT)
    })
    List<Example> listAll();

    @Select(value = "SELECT id, content, gmt_create, gmt_modified, deleted FROM example WHERE id = #{id}")
    @ResultMap(value = "mapRow")
    Example get(String id);

    @Select(value = "SELECT COUNT(*) FROM example")
    long count();

    @Update(value = "UPDATE example content = #{content}, gmt_modified = #{gmtModified} WHERE id = #{id}")
    void update(Example example);
}
