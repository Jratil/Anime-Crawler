package top.ratil.animecrawler.crawler;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

public class PicCrawlerCore implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    public static Map<String, Object>  urlMap = new LinkedHashMap<>();
    private List<String> urlList = new ArrayList<>();

    @Override
    public void process(Page page) {
        if (page.getUrl().regex("http://moe.005.tv/\\d+\\S*\\d*.html").match()) {
            //获取当前页面里面照片的src
            List<String> picList = page.getHtml().xpath("//div[@class='content_nr']//img//@src").all();
            String pageUrl = page.getUrl().get();
            String number = pageUrl.substring(pageUrl.lastIndexOf("/") + 1, pageUrl.lastIndexOf("."));
            //获取当前页面的其他页后面的参数
            List<String> pageList = page.getHtml().xpath("//ul[@class='pagelist']/a//@href")
                    .replace(number, "http://moe.005.tv/" + number)
                    .all();
            //移除所有src为空白的item,好像并没有什么用
            picList.removeAll(Collections.singleton(""));

            page.addTargetRequests(pageList);

            urlList.addAll(picList);
            urlMap.put("picNum", urlList.size());
            urlMap.put("picList", urlList);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
