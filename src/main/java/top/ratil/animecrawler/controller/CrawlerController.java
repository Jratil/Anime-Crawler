package top.ratil.animecrawler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.ratil.animecrawler.annotation.RequestLimit;
import top.ratil.animecrawler.api.Constant.ConstantInfo;
import top.ratil.animecrawler.api.ResultRest;
import top.ratil.animecrawler.service.CrawlerService;
import top.ratil.animecrawler.service.PicUrlService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:8080")
public class CrawlerController {

    @Autowired
    private CrawlerService crawlerService;
    @Autowired
    private PicUrlService picUrlService;

    @RequestLimit(count = 5, time = 60*1000)
    @GetMapping("/api")
    public Object api() {
        String message = "<h2>使用方法:</h2>" +
                "<p><strong>/api：</strong>获取所有api用法</p>" +
                "<p><strong>/api/gallery/{pageNum}/{galleryNum}:</strong> pageNum为页数，galleryNum为该页的第几个图集，默认都为1,eg: /api/gallery/1/1 </p>" +
                "<p><strong>/api/pageUrl/{pageNum}：</strong> pageNum为页数，获取该页数的图集，默认为1，eg: /api/pageUrl/1</p>" +
                "<p><strong>/api/picUrl/{pageNum}:</strong> pageNum为图集中的第几个，因为不是按顺序来，所以默认是76844，这个需要先使用/api/pageUrl获取到图集的pageNum</p>";
        return message;
    }

    @RequestLimit(count = 5, time = 60*1000)
    @GetMapping(value = {"/getPageUrl/{pageNum}", "/api/pageUrl/{pageNum}", "/api/pageUrl"})
    public Object getPageUrl(@PathVariable(value = "pageNum", required = false) String pageNum, HttpServletRequest request) {
        Map<String, Object> urlMap;

        if (pageNum == null) {
            pageNum = "list_3_1.html";
        }
        urlMap = crawlerService.getPageUrl(pageNum);
        return ResultRest.success(urlMap);
    }

    @RequestLimit(count = 5, time = 60*1000)
    @GetMapping(value = {"/getPicUrl/{pageNum}", "/getPicUrl", "/api/picUrl/{pageNum}", "/api/picUrl"})
    public Object getPicUrl(@PathVariable(value = "pageNum", required = false) String pageNum,
                            HttpServletRequest request) throws Exception {
        Map<String, Object> urlMap;

        if (pageNum == null) {
            pageNum = "76844";
        }
        urlMap = crawlerService.getPicUrl(pageNum);
        return ResultRest.success(urlMap);
    }

    @RequestLimit(count = 5, time = 60)
    @GetMapping(value = {"/api/gallery/{pageNum}/{galleryNum}", "/api/gallery"})
    public Object getGalleryUrl(@PathVariable(value = "pageNum", required = false) String pageNum,
                                @PathVariable(value = "galleryNum", required = false) String galleryNum,
                                HttpServletRequest request) throws Exception {
        Map<String, Object> picMap;

        if (pageNum == null) {
            pageNum = "1";
        }
        if (galleryNum == null) {
            galleryNum = "1";
        }

        picMap = picUrlService.findFromSql((Integer.parseInt(pageNum) - 1) * 20 + Integer.parseInt(galleryNum));
        if (picMap != null && picMap.get(ConstantInfo.PICTURE_LIST) != null) {
            return ResultRest.success(picMap);
        }

        //先获取指定页数里面所有图集，然后从图集的list中获取指定的图集
        Map<String, Object> map = crawlerService.getPageUrl(pageNum);
        if (map == null || map.get(ConstantInfo.GALLERY_LIST) == null) {
            return ResultRest.fail("Failed, no found picture");
        }
        List<String> list = (List<String>) map.get(ConstantInfo.GALLERY_LIST);
        String realGalleryNum = list.get(Integer.parseInt(galleryNum) - 1);
        picMap = crawlerService.getPicUrl(realGalleryNum);
        return ResultRest.success(picMap);
    }
}
