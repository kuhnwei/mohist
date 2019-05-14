package com.kuhnwei.mohist.examples.ssm.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/3/14 15:20
 */
@WebAppConfiguration
@ContextConfiguration(value = {"classpath*:spring/spring-context.xml", "classpath*:spring/spring-mvc.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestExampleController {

    @Autowired
    private ExampleController exampleController;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void save() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/example/save")
                .param("exampleStr", "str-" + System.currentTimeMillis()))
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("result : " + result);
    }

    @Test
    public void delete() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/example/delete")
                .param("id", "2"))
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("result : " + result);
    }

    @Test
    public void get() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/example/get")
                .param("id", "1"))
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("result : " + result);
    }

    @Test
    public void list() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/example/list"))
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("result : " + result);
    }

    @Test
    public void count() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/example/count"))
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("result : " + result);
    }

    @Test
    public void update() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/example/update")
                .param("id", "1")
                .param("exampleStr", "str-" + System.currentTimeMillis()))
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        System.out.println("result : " + result);
    }

}
