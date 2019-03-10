package top.ratil.animecrawler.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.ratil.animecrawler.service.PicUrlService;

import java.time.LocalDate;

@SpringBootTest
@RunWith(SpringRunner.class)
public class picUrlServiceImplTest {

    @Autowired
    PicUrlService service;

    @Test
    public void inset2sql() {
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 28; j++){
                service.inset2Sql(LocalDate.of(2019, i, j));
            }
        }
    }

    @Test
    public void findFromSql() {
        service.findFromSql(5);
    }
}