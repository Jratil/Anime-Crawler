package top.ratil.animecrawler.crawler;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.*;
import java.util.stream.Collectors;

public class CrawlerCore implements PageProcessor {

    private Site site = Site.me().setSleepTime(100).setRetryTimes(3);

    public static Map<String, Object> urlMap = new LinkedHashMap<>();

    @Override
    public void process(Page page) {
        if (page.getUrl().regex("http://moe.005.tv/moeimg/tb/list_3_\\d+.html").match()) {
            //得到当前页面链接
            String currentPage = page.getUrl().get();

            //得到当前页面显示的其他页面链接
            List<String> pageList = page.getHtml()
                    .xpath("//ul[@class='pagelist']/a//@href")
                    .replace("list_3_", "")
                    .replace(".html", "")
                    .all()
                    .stream().distinct().collect(Collectors.toList());
            pageList.add(currentPage.substring(currentPage.lastIndexOf("_") + 1, currentPage.lastIndexOf(".")));
            pageList.sort((page1, page2) -> {
                //自定义排序，按数字页数大小
                int one = Integer.parseInt(page1);
                int two = Integer.parseInt(page2);
                return one - two;
            });

            List<String> galleryList = page.getHtml().xpath("//div[@class='zhuti_w_list']/ul/li/a//@href")
                    .replace("http://moe.005.tv/","")
                    .replace(".html", "")
                    .all();

            urlMap.put("pageNum", pageList.size());
            urlMap.put("pageList", pageList);
            urlMap.put("galleryNum", galleryList.size());
            urlMap.put("galleryList", galleryList);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
