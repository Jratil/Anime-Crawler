package top.ratil.animecrawler.crawler;

import top.ratil.animecrawler.api.Constant.StaticInfo;
import top.ratil.animecrawler.entity.Picture;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PicSpiderMan implements PageProcessor {

    private Site site = Site.me().setTimeOut(2000).setCycleRetryTimes(5);

    public static List<Picture> pictureList = new LinkedList<>();

    @Override
    public void process(Page page) {
        if (page.getUrl().regex("http://moe.005.tv/\\d+\\S*\\d*.html").match()) {
            //获取图片的时间信息
            LocalDate spiderDate = StaticInfo.spiderDate;

            //获取当前页面里面照片的src
            List<String> picList = page.getHtml().xpath("//div[@class='content_nr']//img//@src").all();
            String pageUrl = page.getUrl().get();
            String number = pageUrl.substring(pageUrl.lastIndexOf("/") + 1, pageUrl.lastIndexOf("."));
            if (number.matches("\\d+")) {
                pictureList.clear();
                //移除最开始的无用图片
                picList.remove(1);
                picList.remove(0);
                //匹配时间信息
                String msg = page.getHtml().xpath("//div[@class='content_w_box']/span[@class='data']//text()").get();
                Pattern pattern = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}");
                Matcher matcher = pattern.matcher(msg);
                if(matcher.find()) spiderDate = LocalDate.parse(matcher.group());

                //获取当前页面的其他页后面的参数
                List<String> pageList = page.getHtml().xpath("//ul[@class='pagelist']/a//@href")
                        .replace(number, "http://moe.005.tv/" + number)
                        .all();
                page.addTargetRequests(pageList);
            } else {
                number = number.substring(0, number.lastIndexOf("_"));
            }

            for (Iterator it = picList.iterator(); it.hasNext();) {
                Picture picture = new Picture(null, (String)it.next(), spiderDate, null);
                picture.setGalleryUrl("http://moe.005.tv/" + number + ".html");
                pictureList.add(picture);
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
