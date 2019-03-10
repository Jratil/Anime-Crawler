package top.ratil.animecrawler.service.impl;

import org.springframework.stereotype.Service;
import top.ratil.animecrawler.api.Constant.ConstantInfo;
import top.ratil.animecrawler.api.Constant.StaticInfo;
import top.ratil.animecrawler.crawler.CrawlerCore;
import top.ratil.animecrawler.crawler.PicSpiderMan;
import top.ratil.animecrawler.download.PageDownload;
import top.ratil.animecrawler.entity.Picture;
import top.ratil.animecrawler.exception.PageException;
import top.ratil.animecrawler.service.CrawlerService;
import us.codecraft.webmagic.Spider;

import java.util.HashMap;
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

        Map<String, Object> urlMap;
        Spider spider = Spider.create(new CrawlerCore());
        CrawlerCore.urlMap.clear();

        String url = "http://moe.005.tv/moeimg/tb/list_3_" + pageNum + ".html";

        spider.addUrl(url)
                .thread(8)
                .setDownloader(new PageDownload())
                .run();


        if (spider.isExitWhenComplete() && StaticInfo.PAGE_STATUS == 200) {
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

        List<Picture> pictureList;
        Spider spider = Spider.create(new PicSpiderMan());

        String url = "http://moe.005.tv/" + pageNum + ".html";

        spider.addUrl(url)
                .thread(10)
                .setDownloader(new PageDownload())
                .run();

        if (spider.isExitWhenComplete() && StaticInfo.PAGE_STATUS == 200) {
            pictureList = PicSpiderMan.pictureList;
        } else {
            throw new PageException("页面不存在");
        }
        Map<String, Object> map = new HashMap<>();
        map.put(ConstantInfo.PICTURE_NUM, pictureList.size());
        map.put(ConstantInfo.PICTURE_LIST, pictureList);

        return map;
    }
}
