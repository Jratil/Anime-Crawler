package top.ratil.animecrawler.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLimit {

    /**
     * 限制的时间内能够访问的次数
     * @return
     */
    int count() default 10;

    /**
     * 限制访问的时间 毫秒
     * @return
     */
    int time() default 60000;
}
