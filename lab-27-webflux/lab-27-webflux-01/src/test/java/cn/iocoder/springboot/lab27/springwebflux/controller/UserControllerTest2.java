package cn.iocoder.springboot.lab27.springwebflux.controller;

import cn.iocoder.springboot.lab27.springwebflux.service.UserService;
import cn.iocoder.springboot.lab27.springwebflux.vo.UserVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * UserController 单元测试
 *
 * 参考 https://howtodoinjava.com/spring-webflux/webfluxtest-with-webtestclient/ 文章
 */
@RunWith(SpringRunner.class)
/**
 * 添加 @WebFluxTest 注解，传入UserController类，表示要对 UserController 进行单元测试。
 * @WebFluxTest 注解，是包含了 @UserController 的组合注解，它会自动化配置稍后注入的 WebTestClient Bean 对象 mvc 。
 *
 */
@WebFluxTest(UserController.class)
public class UserControllerTest2 {

    /**
     * 通过 webClient 调用后端 API 接口。
     * 但是！每一次调用后端 API 接口，并不会执行真正的后端逻辑，而是走的 Mock 逻辑。
     * 整个逻辑，走的是单元测试，只会启动一个 Mock 的 Spring 环境(仅包含userController接口)。
     */
    @Autowired
    private WebTestClient webClient;

    /**
     * 添加了 @MockBean 注解，
     * 实际这里注入的是一个使用 Mockito 创建的 UserService Mock 代理对象。
     * UserController 中，也会注入一个 UserService 属性，
     * 此时注入的就是该 Mock 出来的 UserService Bean 对象。
     */
    @MockBean
    private UserService userService;

    @Test
    public void testGet2() throws Exception {
        // Mock UserService 的 get 方法
        System.out.println("before mock:" + userService.get(1));
        Mockito.when(userService.get(1)).thenReturn(
                new UserVO().setId(1).setUsername("username:1"));
        System.out.println("after mock:" + userService.get(1));

        // 查询用户列表
        webClient.get().uri("/users/v2/get?id=1")
                .exchange() // 执行请求
                .expectStatus().isOk() // 响应状态码 200
                .expectBody().json("{\n" +
                "    \"id\": 1,\n" +
                "    \"username\": \"username:1\"\n" +
                "}"); // 响应结果
    }

}
