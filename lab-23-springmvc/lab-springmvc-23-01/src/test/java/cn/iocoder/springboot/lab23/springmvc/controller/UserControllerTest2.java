package cn.iocoder.springboot.lab23.springmvc.controller;

import cn.iocoder.springboot.lab23.springmvc.service.UserService;
import cn.iocoder.springboot.lab23.springmvc.vo.UserVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * UserController 单元测试
 *
 * 参考 https://spring.io/guides/gs/testing-web/ 文章
 */
@RunWith(SpringRunner.class)
// 表示要对 UserController 进行单元测试。
@WebMvcTest(UserController.class)
public class UserControllerTest2 {

    @Autowired
    private MockMvc mvc;

    // 实际这里注入的是一个使用 Mockito 创建的 UserService Mock 代理对象。
    @MockBean
    private UserService userService;

    @Test
    public void testGet2() throws Exception {
        // Mock UserService 的 get 方法
        // 此时的 userService 是通过 Mockito 来 Mock 出来的对象，其所有调用它的方法，返回的都是空。
        System.out.println("before mock:" + userService.get(1));
        // 通过 Mockito 进行 Mock userService 的 #get(Integer id) 方法，当传入的 id = 1 方法参数时，
        // 返回 id = 1 并且 username = "username:1" 的 UserVO 对象。
        Mockito.when(userService.get(1)).thenReturn(new UserVO().setId(1).setUsername("username:1"));
        // Mock 返回的 UserVO 对象
        System.out.println("after mock:" + userService.get(1));

        // 使用 mvc 完成一次后端 API 调用，并进行断言结果是否正确。执行成功，单元测试通过。
        // 查询用户列表
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/users/v2/1"));
        // 校验结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()); // 响应状态码 200
        resultActions.andExpect(MockMvcResultMatchers.content().json("{\n" +
                "    \"id\": 1,\n" +
                "    \"username\": \"username:1\"\n" +
                "}")); // 响应结果
    }

}
