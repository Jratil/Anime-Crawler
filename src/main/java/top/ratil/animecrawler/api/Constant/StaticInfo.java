package top.ratil.animecrawler.api.Constant;

import java.time.LocalDate;

public class StaticInfo {
    /**
     * 用来保存请求网页后返回的status code
     */
    public static int PAGE_STATUS = 404;

    /**
     * 默认的爬取时间
     */
    public static LocalDate spiderDate = LocalDate.now().minusDays(1);
}
