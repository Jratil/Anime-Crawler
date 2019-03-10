package top.ratil.animecrawler.crawler;

import top.ratil.animecrawler.api.Constant.StaticInfo;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class SpiderMan implements PageProcessor {

    private Site site = Site.me().setTimeOut(2000).setCycleRetryTimes(5);

    public static List<String> urlList = new LinkedList<>();
    private int number = 1;

    @Override
    public void process(Page page) {
        if (page.getUrl().regex("http://moe.005.tv/moeimg/tb/list_3_\\d+.html").match()) {

            LocalDate spiderDate = StaticInfo.spiderDate;

            Selectable selectable = page.getHtml().xpath("//div[@class='zhuti_w_list']/ul/li/span");
            List<String> urlAndDate = selectable.xpath("//a//@href | //dt//text()").all();
            List<String> realUrl = new LinkedList<>();
            for (int i = 1; i < urlAndDate.size(); i = i + 2) {
                if (urlAndDate.get(i).equals(spiderDate.toString())) {
                    realUrl.add(urlAndDate.get(i - 1));
                }
            }
            //如果当前页没有查找到符合时间的图集，并且最后一个图集的时间大于查找的时间，则把下一页添加到爬取列表
            if(realUrl.size() == 0 && urlAndDate.get(urlAndDate.size() - 1).compareTo(spiderDate.toString()) > 0) {
                number++;
                page.addTargetRequest("http://moe.005.tv/moeimg/tb/list_3_"+ number +".html");
            } else if (urlAndDate.get(urlAndDate.size() - 1).equals(spiderDate.toString())) {
                //如果当前页最后一个图集的时间等于爬取的时间，则将下一页加入爬取列表
                number++;
                page.addTargetRequest("http://moe.005.tv/moeimg/tb/list_3_"+ number +".html");
            }
            urlList.addAll(realUrl);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
