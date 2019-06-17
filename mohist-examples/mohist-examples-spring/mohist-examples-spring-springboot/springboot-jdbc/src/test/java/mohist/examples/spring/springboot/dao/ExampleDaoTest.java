package mohist.examples.spring.springboot.dao;

import mohist.examples.spring.springboot.SpringBootBaseTest;
import mohist.examples.spring.springboot.entity.Example;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/13
 **/
public class ExampleDaoTest extends SpringBootBaseTest {
    @Autowired
    private ExampleDao exampleDao;

    @Test
    public void save() {
       for (int i = 0; i < 10; i ++) {
           Example obj = new Example();
           obj.setContent("TEST - " + System.currentTimeMillis());
           this.exampleDao.save(obj);
       }
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
        this.exampleDao.get("1e58b891da9248b4ac37620dfc7ad821");
    }

    @Test
    public void listAll() {
        System.out.println(this.exampleDao.listAll().size());
    }
}
