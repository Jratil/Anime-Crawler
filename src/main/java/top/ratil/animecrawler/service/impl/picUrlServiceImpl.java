package top.ratil.animecrawler.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.ratil.animecrawler.api.Constant.ConstantInfo;
import top.ratil.animecrawler.api.Constant.StaticInfo;
import top.ratil.animecrawler.crawler.PicSpiderMan;
import top.ratil.animecrawler.crawler.SpiderMan;
import top.ratil.animecrawler.download.PageDownload;
import top.ratil.animecrawler.entity.Gallery;
import top.ratil.animecrawler.entity.Picture;
import top.ratil.animecrawler.mapper.GalleryMapper;
import top.ratil.animecrawler.mapper.PictureMapper;
import top.ratil.animecrawler.service.PicUrlService;
import top.ratil.animecrawler.util.RedisUtils;
import us.codecraft.webmagic.Spider;

import java.time.LocalDate;
import java.util.*;

@Service
public class picUrlServiceImpl implements PicUrlService {

    @Autowired
    private GalleryMapper galleryMapper;
    @Autowired
    private PictureMapper pictureMapper;
    @Autowired
    private RedisUtils redisUtils;

    public Map<String, Object> findFromSql(int position) {

        List<Picture> pictureList;

        Gallery gallery = (Gallery) redisUtils.get(String.valueOf(position - 1));
        if (gallery == null) {
            gallery = galleryMapper.selectByPosition(position - 1);
            if (gallery == null) {
                return null;
            }
            redisUtils.set(String.valueOf(position - 1), gallery);
        }


        pictureList = (List<Picture>) redisUtils.get(gallery.getGalleryUrl());
        if (pictureList == null || pictureList.size() == 0) {
            pictureList = pictureMapper.selectByGallery(gallery.getGalleryUrl());
            redisUtils.set(gallery.getGalleryUrl(), pictureList);
        }

        Map<String, Object> map = new HashMap<>();
        map.put(ConstantInfo.PICTURE_NUM, pictureList.size());
        map.put(ConstantInfo.PICTURE_LIST, pictureList);

        return map;
    }

    @Override
    public int inset2Sql(LocalDate date) {

        List<Gallery> galleryList = new LinkedList<>();
        List<Picture> pictureList = new LinkedList<>();

        LocalDate spiderDate = date == null ? StaticInfo.spiderDate : date;
        StaticInfo.spiderDate = spiderDate;

        Spider pageSpider = Spider.create(new SpiderMan());
        SpiderMan.urlList.clear();
        String startUrl = "http://moe.005.tv/moeimg/tb/list_3_1.html";
        pageSpider.addUrl(startUrl)
                .thread(5)
                .setDownloader(new PageDownload())
                .run();
        if (pageSpider.isExitWhenComplete()) {
            List<String> pageUrlList = SpiderMan.urlList;
            pageUrlList.forEach(url -> {
                int picNum = 0;
                Spider picSpider = Spider.create(new PicSpiderMan());
                picSpider.addUrl(url)
                        .thread(5)
                        .setDownloader(new PageDownload())
                        .run();

                if (picSpider.isExitWhenComplete()) {
                    picNum = PicSpiderMan.pictureList.size();
                    pictureList.addAll(PicSpiderMan.pictureList);
                }
                Gallery gallery = new Gallery(null, picNum, spiderDate, url);
                galleryList.add(gallery);
            });
        }

        if (galleryList.size() > 0) {
            int num = galleryMapper.insertByList(galleryList);
            if (num > 0) {
                pictureList.forEach(picture -> pictureMapper.insertByPicture(picture));
            }
            return num;
        }
        return 0;
    }
}
