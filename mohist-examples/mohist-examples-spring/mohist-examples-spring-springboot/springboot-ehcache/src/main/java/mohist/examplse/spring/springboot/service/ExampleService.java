package mohist.examplse.spring.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/13
 **/
@Service
public class ExampleService {
    private final static String CACHE_NAME = "ExampleServiceEhcache";
    private final static String CACHE_KEY_ALL = "All";
    private Cache cache;

    @Autowired
    private CacheManager cacheManager;

    public void save(Object object) {
        this.getCache().put("object.id", object);
        this.getCache().evict(CACHE_KEY_ALL);
    }

    public void remove(Object object) {
        this.getCache().evict("object.id");
        this.getCache().evict(CACHE_KEY_ALL);
    }

    public Object getById(long id) {
        return this.getCache().get(id, Object.class);
    }

    public List<Object> listAll() {
        return (List<Object>) this.getCache().get(CACHE_KEY_ALL);
    }

    private Cache getCache() {
        if (Objects.isNull(this.cache)) {
            this.cache = this.cacheManager.getCache(CACHE_NAME);
            return this.cache;
        }
        return this.cache;
    }
}
