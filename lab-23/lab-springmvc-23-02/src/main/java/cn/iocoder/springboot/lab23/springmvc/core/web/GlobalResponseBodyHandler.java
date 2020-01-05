package cn.iocoder.springboot.lab23.springmvc.core.web;

import cn.iocoder.springboot.lab23.springmvc.core.vo.CommonResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

// 通过实现 ResponseBodyAdvice 接口，并添加 @ControllerAdvice 接口，拦截 Controller 的返回结果。
// 只拦截 Controller 所在包，避免其它类似 swagger 提供的 API 被切面拦截
@ControllerAdvice(basePackages = "cn.iocoder.springboot.lab23.springmvc.controller")
public class GlobalResponseBodyHandler implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // 表示拦截 Controller 所有 API 接口的返回结果。
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        // 如果已经是 CommonResult 类型，则直接返回
        if (body instanceof CommonResult) {
            return body;
        }
        // 如果不是，则包装成 CommonResult 类型
        // API 接口返回结果，被 GlobalResponseBodyHandler 拦截到，约定就是成功返回，
        // 所以使用 CommonResult#success(T data) 方法，进行包装成成功的 CommonResult 返回。
        return CommonResult.success(body);
    }

}
