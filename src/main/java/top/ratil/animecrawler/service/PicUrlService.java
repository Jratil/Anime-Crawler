package top.ratil.animecrawler.service;

import java.time.LocalDate;
import java.util.Map;

public interface PicUrlService {

    Map<String, Object> findFromSql(int position);

    int inset2Sql(LocalDate date);
}
