package top.ratil.animecrawler;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = "top.ratil.animecrawler")
@ServletComponentScan
@MapperScan("top.ratil.animecrawler.mapper")
public class AnimecrawlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimecrawlerApplication.class, args);
    }

}