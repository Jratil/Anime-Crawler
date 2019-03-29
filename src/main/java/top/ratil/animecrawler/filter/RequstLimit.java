package top.ratil.animecrawler.filter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.ratil.animecrawler.annotation.RequestLimit;
import top.ratil.animecrawler.exception.RequestLimitException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@Component
@Aspect
public class RequstLimit {

    private Map<String, Integer> limitMap = new HashMap<>();

    @Before(value = "execution(* top.ratil.animecrawler.controller.CrawlerController.*(..)) && @annotation(requestLimit)")
    public void limit(RequestLimit requestLimit) throws RequestLimitException {

        //获取Request
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        if (request == null) {
            throw new RequestLimitException("缺失Request");
        }

        //获取请求的地址和api当作key来判断请求次数
        String ip = request.getLocalAddr();
        String url = request.getRequestURL().toString();
        String key = "req_".concat(url).concat(ip);
        if (limitMap.get(key) == null || limitMap.get(key) == 0) {
            limitMap.put(key, 1);
        } else {
            limitMap.put(key, limitMap.get(key) + 1);
        }

        int requestCount = limitMap.get(key);
        //判断有没有请求过，如果请求过则再规定时间后清除请求次数，从0开始计算请求次数
        if (requestCount > 0) {
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    limitMap.remove(key);
                }
            };
            timer.schedule(task, requestLimit.time());
        }

        if (requestCount > requestLimit.count()) {
            throw new RequestLimitException("超过访问次数！");
        }
    }
}
