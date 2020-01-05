package cn.iocoder.springboot.lab23.springmvc.core.web;

import cn.iocoder.springboot.lab23.springmvc.constants.ServiceExceptionEnum;
import cn.iocoder.springboot.lab23.springmvc.core.exception.ServiceException;
import cn.iocoder.springboot.lab23.springmvc.core.vo.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局统一返回的处理器。
 * 通过添加 @ExceptionHandler 注解，定义每个方法对应处理的异常。
 * 添加了 @ResponseBody 注解，标记直接使用返回结果作为 API 的响应。
 */
@ControllerAdvice(basePackages = "cn.iocoder.springboot.lab23.springmvc.controller")
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理 ServiceException 异常
     * 直接使用该异常的 code + message 属性，构建出 CommonResult 对象返回。
     */
    @ResponseBody
    @ExceptionHandler(value = ServiceException.class)
    public CommonResult serviceExceptionHandler(HttpServletRequest req, ServiceException ex) {
        logger.debug("[serviceExceptionHandler]", ex);
        // 包装 CommonResult 结果
        return CommonResult.error(ex.getCode(), ex.getMessage());
    }

    /**
     * 处理 MissingServletRequestParameterException 异常
     *
     * SpringMVC 参数不正确
     */
    @ResponseBody
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public CommonResult missingServletRequestParameterExceptionHandler(HttpServletRequest req, MissingServletRequestParameterException ex) {
        logger.debug("[missingServletRequestParameterExceptionHandler]", ex);
        // 包装 CommonResult 结果
        return CommonResult.error(ServiceExceptionEnum.MISSING_REQUEST_PARAM_ERROR.getCode(),
                ServiceExceptionEnum.MISSING_REQUEST_PARAM_ERROR.getMessage());
    }

    /**
     * 处理其它 Exception 异常
     * 兜底的异常处理，避免有一些其它异常，没有在 GlobalExceptionHandler 中，提供自定义的处理方式。
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public CommonResult exceptionHandler(HttpServletRequest req, Exception e) {
        /**
         * 在 #exceptionHandler(...) 方法中，使用 logger 打印了错误日志，
         * 方便接入 ELK 等日志服务，发起告警，通知我们去排查解决。
         * 如果系统里暂时没有日志服务，可以记录错误日志到数据库中，也是不错的选择。
         * 而其它两个方法，因为是更偏业务的，相对正常的异常，所以无需记录错误日志。
         */
        // 记录异常日志
        logger.error("[exceptionHandler]", e);
        // 返回 ERROR CommonResult
        return CommonResult.error(ServiceExceptionEnum.SYS_ERROR.getCode(),
                ServiceExceptionEnum.SYS_ERROR.getMessage());
    }

}
