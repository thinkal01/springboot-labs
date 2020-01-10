package cn.iocoder.springboot.lab23.springmvc.controller;

import cn.iocoder.springboot.lab23.springmvc.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * UserController 集成测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
/**
 * 添加了 @AutoConfigureMockMvc 注解，
 * 用于自动化配置注入的 MockMvc Bean 对象 mvc。
 * 通过 mvc 调用后端 API 接口。而每一次调用后端 API 接口，都会执行真正的后端逻辑。
 * 因此，整个逻辑，走的是集成测试，会启动一个真实的 Spring 环境。
 */
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testList() throws Exception {
        /**
         * 每次 API 接口的请求，都通过 MockMvcRequestBuilders 来构建。
         * 构建完成后，通过 mvc 执行请求，返回 ResultActions 结果
         */
        // 查询用户列表
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/users"));
        /**
         * 执行完请求后，通过调用 ResultActions 的 andExpect(ResultMatcher matcher) 方法，添加对结果的预期，
         * 相当于做断言。如果不符合预期，则会抛出异常，测试不通过。
         * #andDo(ResultHandler handler) 方法，添加 ResultHandler 结果处理器，
         * 例如说调试时打印结果到控制台，看结果是否正确。
         * #andReturn() 方法，最后返回相应的 MvcResult 结果。
         * 后续，自己针对 MvcResult 写一些自定义的逻辑。
         */
        // 校验结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()); // 响应状态码 200
        resultActions.andExpect(MockMvcResultMatchers.content().json("[\n" +
                "    {\n" +
                "        \"id\": 1,\n" +
                "        \"username\": \"yudaoyuanma\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 2,\n" +
                "        \"username\": \"woshiyutou\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 3,\n" +
                "        \"username\": \"chifanshuijiao\"\n" +
                "    }\n" +
                "]")); // 响应结果
    }

    @Test
    public void testGet() throws Exception {
        // 获得指定用户编号的用户
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/users/1"));
        // 校验结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()); // 响应状态码 200
        resultActions.andExpect(MockMvcResultMatchers.content().json("{\n" +
                "\"id\": 1,\n" +
                "\"username\": \"username:1\"\n" +
                "}")); // 响应结果
    }

    @Test
    public void testGet2() throws Exception {
        // 获得指定用户编号的用户
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/users/1"));
        // 校验结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()); // 响应状态码 200
        resultActions.andExpect(MockMvcResultMatchers.content().json("{\n" +
                "\"id\": 1,\n" +
                "\"username\": \"username:1\"\n" +
                "}")); // 响应结果

        // 打印请求和响应信息
        resultActions.andDo(MockMvcResultHandlers.print());

        // 获得 MvcResult，打印下拦截器的数量。后续执行各种自定义逻辑
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("拦截器数量：" + mvcResult.getInterceptors().length);
    }

    @Test
    public void testAdd() throws Exception {
        // 获得指定用户编号的用户
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/users")
                .param("username", "yudaoyuanma")
                .param("passowrd", "nicai"));
        // 校验结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()); // 响应状态码 200
        resultActions.andExpect(MockMvcResultMatchers.content().string("1")); // 响应结果
    }

    @Test
    public void testUpdate() throws Exception {
        // 获得指定用户编号的用户
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.put("/users/1")
                .param("username", "yudaoyuanma")
                .param("passowrd", "nicai"));
        // 校验结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()); // 响应状态码 200
        resultActions.andExpect(MockMvcResultMatchers.content().string("true")); // 响应结果
    }

    @Test
    public void testDelete() throws Exception {
        // 获得指定用户编号的用户
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.delete("/users/1"));
        // 校验结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()); // 响应状态码 200
        resultActions.andExpect(MockMvcResultMatchers.content().string("false")); // 响应结果
    }

}
