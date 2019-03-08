package top.ratil.animecrawler.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.ratil.animecrawler.api.ResultRest;
import top.ratil.animecrawler.api.status.PageStatus;

@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(PageException.class)
    public Object pageExcepation(RuntimeException e) {
        return ResultRest.fail(PageStatus.PAGE_STATUS, e.getMessage());
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public Object indexException(IndexOutOfBoundsException e) {
        return ResultRest.fail(404, "访问参数不存在");
    }
}
