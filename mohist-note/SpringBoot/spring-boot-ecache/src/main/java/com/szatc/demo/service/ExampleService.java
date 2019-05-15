package com.szatc.demo.service;

import com.szatc.demo.domain.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * ehcache 的使用demo
 * @author Kuhn Wei, email@kuhnwei.com
 * @date 2018/6/11 9:06
 **/
@Service
public class ExampleService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Map<Long, Example> exampleMap = Collections.synchronizedMap(new HashMap<>());

    /**
     * 保存数据，并把数据保存至缓存中
     * <p>
     *     CachePut ：保存数据到缓存中，前提条件就是需要保存方法定义返回值，
     *     并且返回值为将要保存的数据，不然数据无法保存到缓存中
     * </p>
     * @param example 数据
     * @return 保存的数据
     */
    @CachePut(value = "testEhcache", key = "#example.id")
    public Example save(Example example) {
        if (Objects.isNull(example)) {
            throw new NullPointerException("要保存的对象不能为null");
        }
        logger.info("-->> 保存数据：" + example);
        exampleMap.put(example.getId(), example);
        return example;
    }

    /**
     * 删除数据，同时删除缓存中的数据
     * @param id id
     */
    @CacheEvict(value = "testEhcache")
    public void remove(Long id) {
        if (Objects.isNull(id)) {
            throw new NullPointerException("id为null，无法完成删除操作");
        }
        logger.info("-->> 删除数据：" + exampleMap.remove(id));
    }

    /**
     * 查询数据，优先从缓存中查找，如果缓存中不存在就从数据库中查找，并把查找到的数据保存至缓存中
     * @param id id
     * @return 数据
     */
    @Cacheable(value = "testEhcache", key = "#id", unless = "#result==null")
    public Example get(Long id) {
        if (!exampleMap.containsKey(id)) {
            logger.info("-->> 数据不存在：(id)" + id);
            return null;
        }
        Example result = exampleMap.get(id);
        logger.info("-->> 获取数据：" + result);
        return result;
    }
}
