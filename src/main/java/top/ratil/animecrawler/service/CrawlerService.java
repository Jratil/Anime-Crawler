package top.ratil.animecrawler.service;

import java.util.List;
import java.util.Map;

public interface CrawlerService {

    Map<String, Object> getPageUrl(String pageNum);

    Map<String, Object> getPicUrl(String pageNum) throws Exception;

}
