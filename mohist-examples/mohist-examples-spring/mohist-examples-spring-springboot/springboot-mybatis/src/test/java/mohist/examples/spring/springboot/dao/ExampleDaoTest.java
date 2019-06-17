package mohist.examples.spring.springboot.dao;

import mohist.examples.spring.springboot.SpringBootBaseTest;
import mohist.examples.spring.springboot.entity.Example;
import org.junit.Test;
import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/13
 **/
public class ExampleDaoTest extends SpringBootBaseTest {

    @Resource
    private ExampleDao exampleDao;

    @Test
    public void save() {
        Example obj = new Example();
        obj.setId(UUID.randomUUID().toString().replace("-", ""));
        long currentTimeMillis = System.currentTimeMillis();
        obj.setContent("TEST - " + currentTimeMillis);
        obj.setGmtCreate(currentTimeMillis);
        obj.setGmtModified(currentTimeMillis);
        obj.setDeleted(false);
        this.exampleDao.save(obj);
    }

    @Test
    public void remove() {
        this.exampleDao.remove("0ca8c62b-b8f0-4286-9b30-6a3a5d94");
    }

    @Test
    public void delete() {
        this.exampleDao.delete("69752e52-ce2f-4a4b-8bb7-398d77b7334e");
    }

    @Test
    public void get() {
        System.out.println(this.exampleDao.get("1e58b891da9248b4ac37620dfc7ad826"));
    }

    @Test
    public void listAll() {
        System.out.println(this.exampleDao.listAll().size());
    }
}
