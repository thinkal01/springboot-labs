package cn.iocoder.springboot.lab27.springwebflux.controller;

import cn.iocoder.springboot.lab27.springwebflux.dto.UserAddDTO;
import cn.iocoder.springboot.lab27.springwebflux.dto.UserUpdateDTO;
import cn.iocoder.springboot.lab27.springwebflux.service.UserService;
import cn.iocoder.springboot.lab27.springwebflux.vo.UserVO;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于 Annotated Controller 方式实现
 * 用户 Controller
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询用户列表
     *
     * @return 用户列表
     */
    @GetMapping("/list")
    public Flux<UserVO> list() {
        // 查询列表
        List<UserVO> result = new ArrayList<>();
        result.add(new UserVO().setId(1).setUsername("yudaoyuanma"));
        result.add(new UserVO().setId(2).setUsername("woshiyutou"));
        result.add(new UserVO().setId(3).setUsername("chifanshuijiao"));
        // 调用 Flux#fromIterable(Iterable<? extends T> it) 方法，将List包装成Flux对象返回
        return Flux.fromIterable(result);
    }

    /**
     * 获得指定用户编号的用户
     *
     * @param id 用户编号
     * @return 用户
     */
    @GetMapping("/get")
    public Mono<UserVO> get(@RequestParam("id") Integer id) {
        // 查询用户
        UserVO user = new UserVO().setId(id).setUsername("username:" + id);
        // 最终调用 Mono#just(T data) 方法，将 UserVO 包装成 Mono 对象返回。
        return Mono.just(user);
    }

    /**
     * 获得指定用户编号的用户
     *
     * @param id 用户编号
     * @return 用户
     */
    @GetMapping("/v2/get")
    public Mono<UserVO> get2(@RequestParam("id") Integer id) {
        // 查询用户
        UserVO user = userService.get(id);
        // 返回
        return Mono.just(user);
    }

    /**
     * 添加用户
     * @param addDTO 添加用户信息 DTO
     * @return 添加成功的用户编号
     */
    @PostMapping("add")
    public Mono<Integer> add(@RequestBody Publisher<UserAddDTO> addDTO) {
        // 插入用户记录，返回编号
        Integer returnId = 1;
        // 返回用户编号
        return Mono.just(returnId);
    }

    /**
     * 添加用户
     * 也可以使用 application/x-www-form-urlencoded 或 multipart/form-data 这两个 Content-Type 内容类型，
     * 通过 request 的 Form Data 或 Multipart Data 传递参数。
     *
     * 也可以直接使用参数为 UserAddDTO 类型。如果后续需要使用到 Reactor API ，
     * 则调用 Mono#just(T data) 方法，封装出 Publisher 对象。
     * Flux 和 Mono 都实现了 Publisher 接口。
     * @param addDTO 添加用户信息 DTO
     * @return 添加成功的用户编号
     */
    @PostMapping("add2")
    public Mono<Integer> add2(Mono<UserAddDTO> addDTO) {
        // 插入用户记录，返回编号
        Integer returnId = 1;
        // 返回用户编号
        return Mono.just(returnId);
    }

    /**
     * 更新指定用户编号的用户
     *
     * @param updateDTO 更新用户信息 DTO
     * @return 是否修改成功
     */
    @PostMapping("/update")
    public Mono<Boolean> update(@RequestBody Publisher<UserUpdateDTO> updateDTO) {
        // 更新用户记录
        Boolean success = true;
        // 返回更新是否成功
        return Mono.just(success);
    }

    /**
     * 删除指定用户编号的用户
     *
     * @param id 用户编号
     * @return 是否删除成功
     */
    @PostMapping("/delete") // URL 修改成 /delete ，RequestMethod 改成 DELETE
    public Mono<Boolean> delete(@RequestParam("id") Integer id) {
        // 删除用户记录
        Boolean success = true;
        // 返回是否更新成功
        return Mono.just(success);
    }

}
