package top.ratil.animecrawler.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.ratil.animecrawler.api.status.PageStatus;
import top.ratil.animecrawler.crawler.CrawlerCore;
import top.ratil.animecrawler.crawler.PicCrawlerCore;
import top.ratil.animecrawler.download.PageDownload;
import top.ratil.animecrawler.exception.PageException;
import top.ratil.animecrawler.service.CrawlerService;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.utils.HttpClientUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class CrawlerServiceImpl implements CrawlerService {

    @Override
    public Map<String, Object> getPageUrl(String pageNum) {

        if (!Pattern.matches("\\d+", pageNum)) {
            throw new PageException("参数有误");
        }

        Map<String, Object> urlMap = new LinkedHashMap<>();
        Spider spider = Spider.create(new CrawlerCore());
        CrawlerCore.urlMap.clear();

        String url = "http://moe.005.tv/moeimg/tb/list_3_" + pageNum + ".html";

        spider.addUrl(url)
                .thread(8)
                .setDownloader(new PageDownload())
                .run();


        if (spider.isExitWhenComplete() && PageStatus.PAGE_STATUS == 200) {
            urlMap = CrawlerCore.urlMap;
        } else {
            throw new PageException("页面不存在");
        }
        return urlMap;
    }

    @Override
    public Map<String, Object> getPicUrl(String pageNum) {

        if (!Pattern.matches("\\d+", pageNum)) {
            throw new PageException("参数有误");
        }

        Map<String, Object> urlMap = new LinkedHashMap<>();
        Spider spider = Spider.create(new PicCrawlerCore());
        PicCrawlerCore.urlMap.clear();

        String url = "http://moe.005.tv/" + pageNum + ".html";

        // 保存抓取状态
        final Integer[] crawlStatus = {0};
        spider.addUrl(url)
                .thread(10)
                .setDownloader(new PageDownload())
                .run();

        if (spider.isExitWhenComplete() && PageStatus.PAGE_STATUS == 200) {
            urlMap = PicCrawlerCore.urlMap;
        } else {
            throw new PageException("页面不存在");
        }
        return urlMap;
    }
}
