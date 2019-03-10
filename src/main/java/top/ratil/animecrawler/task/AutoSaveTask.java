package top.ratil.animecrawler.task;

import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.ratil.animecrawler.service.PicUrlService;

import java.time.LocalDate;

@Component
@EnableScheduling
public class AutoSaveTask {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PicUrlService picUrlService;

    @Scheduled(cron = "0 44 16 * * ?")
    public void saveTask() {
        LocalDate spiderDate = LocalDate.now().minusDays(1);
        int num = picUrlService.inset2Sql(spiderDate);
        logger.info("当前时间：{} == 执行成功，爬取到日期为: {} 的 {} 个图集", LocalDate.now(),spiderDate, num);
    }
}
