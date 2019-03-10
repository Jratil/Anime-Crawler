package top.ratil.animecrawler.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.ratil.animecrawler.api.ResultRest;
import top.ratil.animecrawler.api.Constant.StaticInfo;
import top.ratil.animecrawler.exception.PageException;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一异常处理和 400， 500 错误处理
 */
@RestControllerAdvice
@RestController
public class GlobalExceptionController implements ErrorController {

    @ExceptionHandler(PageException.class)
    public Object pageException(RuntimeException e) {
        return ResultRest.fail(StaticInfo.PAGE_STATUS, e.getMessage());
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public Object indexException(IndexOutOfBoundsException e) {
        return ResultRest.fail(404, "访问参数不存在");
    }

    /**
     * 400 和 500 错误处理
     * @param request
     * @return
     */
    @RequestMapping("/error")
    public Object error404(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        return ResultRest.fail(statusCode, "页面不存在");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
